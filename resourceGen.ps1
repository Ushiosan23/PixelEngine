$currentLocation = $PSScriptRoot
$packageClass = "fx.soft.pixelengine"
$targetLocation = [System.IO.Path]::Combine($currentLocation, "src", "main", "java", "fx", "soft", "pixelengine", "R.java")
$resourcesLocaion = [System.IO.Path]::Combine($currentLocation, "src", "main", "resources")

function CreateFileIfNotExists
{
    $exists = [System.IO.File]::Exists($targetLocation);

    if ($exists -ine $true)
    {
        Write-Output $targetLocation
        New-Item -Path $targetLocation -Type File
    }
}

function GetClassHearder
{
    $content = "// This file is generated automatically.`n"
    $content += "package $packageClass;`n`n"
    $content += "import java.net.URL;`n"
    $content += "import java.io.InputStream;`n"
    $content += "import org.jetbrains.annotations.NotNull;`n`n"

    $content += "/**`n"
    $content += " * Resource class manager`n"
    $content += " */`n"
    $content += "public final class R {`n`n"
    return $content
}

function GetClassResourceFiles
{

    $content = ""

    foreach ($item in GetResourceFiles)
    {
        if ($item.GetType() -eq [System.IO.FileInfo])
        {
            $fInfo = [System.IO.FileInfo]$item

            $varName = GetReadableVar $fInfo
            $varLocation = GetRelativePath $fInfo

            Write-Host "-------------- $varName --------------"
            Write-Host "FullName Path:      $( $fInfo.FullName )"
            Write-Host "Relative Path:      $varLocation`n"

            $content += "`t// Resource location $varLocation`n";
            $content += "`tpublic static final Resource $varName = new Resource(""$varLocation"");`n`n"
        }
    }

    return $content
}

function GetClassFooter
{
    $content = "`n`n"
    $content += "}"

    return $content
}

function GetClassInternalResource
{
    $content = "`t/**`n"
    $content += "`t * Resource class used to manage resource files`n"
    $content += "`t */`n"
    $content += "`tpublic static class Resource {`n`n"

    $content += "`t`t/**`n"
    $content += "`t`t * Resource location`n"
    $content += "`t`t */`n"
    $content += "`t`tpublic final String location;`n`n"

    $content += "`t`t/**`n"
    $content += "`t`t * Resource class loader`n"
    $content += "`t`t */`n"
    $content += "`t`tprivate final ClassLoader classLoader;`n`n"

    $content += "`t`t/**`n"
    $content += "`t`t * Resource constructor`n"
    $content += "`t`t * `n"
    $content += "`t`t * @param name relative resource path`n"
    $content += "`t`t */`n"
    $content += "`t`tpublic Resource(@NotNull String name) {`n"
    $content += "`t`t`tlocation = name;`n"
    $content += "`t`t`tclassLoader = ClassLoader.getSystemClassLoader();`n"
    $content += "`t`t}`n`n"

    $content += "`t`t/**`n"
    $content += "`t`t * Get resource url location`n"
    $content += "`t`t * `n"
    $content += "`t`t * @return {@link URL} resource url object`n"
    $content += "`t`t */`n"
    $content += "`t`tpublic URL getURL() {`n"
    $content += "`t`t`treturn classLoader.getResource(location);`n"
    $content += "`t`t}`n`n"

    $content += "`t`t/**`n"
    $content += "`t`t * Get resource input stream`n"
    $content += "`t`t * `n"
    $content += "`t`t * @return {@link InputStream} resource stream`n"
    $content += "`t`t */`n"
    $content += "`t`tpublic InputStream getInputStream() {`n"
    $content += "`t`t`ttry {`n"
    $content += "`t`t`t`treturn classLoader.getResourceAsStream(location);`n"
    $content += "`t`t`t} catch (Exception err) {`n"
    $content += "`t`t`t`treturn null;`n"
    $content += "`t`t`t}`n"
    $content += "`t`t}`n`n"

    $content += "`t}"
    return $content
}

function GetResourceFiles
{
    return Get-ChildItem -Path $resourcesLocaion -Recurse
}

function GetRelativePath
{
    param (
        [System.IO.FileInfo] $FileInfo
    )

    $rawRelative = $FileInfo.FullName.Replace($resourcesLocaion, "")
    $rawRelative = $rawRelative.Substring(1, $rawRelative.Length - 1)

    return $rawRelative.Replace("\", "/")
}

function GetReadableVar
{
    param (
        [System.IO.FileInfo] $FileInfo
    )

    $fileName = GetRelativePath $FileInfo
    $fileName = $fileName.Replace("\", "_")
    $fileName = $fileName.Replace("/", "_")
    $fileName = $fileName.Replace("-", "_")
    $fileName = $fileName.Replace($FileInfo.Extension, "")

    return $fileName.ToLower()
}

function UpdateContent
{
    CreateFileIfNotExists

    $content = GetClassHearder
    $content += GetClassResourceFiles
    $content += GetClassInternalResource
    $content += GetClassFooter

    Set-Content -Path $targetLocation -Value $content
}


UpdateContent
