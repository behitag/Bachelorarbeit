Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

$sourceX = 0
$sourceY = 0
$width = 1340
$height = 1000
$name_pid = "D:\z\name_pid.csv"
if (-Not (Test-Path -Path $name_pid)) { New-Item -Path $name_pid -ItemType File }

$whitelist = [System.Collections.ArrayList]::new()
$whitelist.AddRange(@("vmmem", "com.docker.backend", "com.docker.build", "com.docker.dev-envs", "Docker Desktop", "docker-compose"))
$whitelist.AddRange(@("wsl", "wslhost", "wslrelay", "wslservice"))
$whitelist.AddRange(@("postgres", "pg_ctl"))
$whitelist.AddRange(@("erl", "erlsrv"))
$whitelist.AddRange(@("java", "javaw"))

function doAffinityAndSaveToCSV {
    $allProcesses = Get-Process
    $PIDs = [System.Collections.ArrayList]::new()
    $time = Get-Date -Format "yyyy-MM-dd HH:mm:ss.fff"

    foreach($process in $allProcesses){	
    if ($process.ProcessorAffinity -eq $null -or $process.ProcessorAffinity -ne 0x1) { $PIDs.Add("$($process.Name)_$($process.Id)") | Out-Null }}

    $row = "$time," + ($PIDs -join ",")
    $row | Out-File -FilePath $name_pid -Append

    foreach($process in $allProcesses){	
    if($whitelist -contains $process.Name) { try {$process.ProcessorAffinity = 0xE } catch{}}
    else { try{$process.ProcessorAffinity = 0x1 } catch{}}}
}

function takeScreenshot {
    $name = Get-Date -Format "yyyy-MM-dd-HH-mm-ss"
    $png_path = "D:\z\" + $name.ToString() + ".png"
    $bitmap = New-Object System.Drawing.Bitmap $width, $height
    $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
    $graphics.CopyFromScreen($sourceX, $sourceY, 0, 0, [System.Drawing.Size]::new($width, $height))
    $bitmap.Save($png_path, [System.Drawing.Imaging.ImageFormat]::Png)
    $graphics.Dispose()
    $bitmap.Dispose()     
}

echo "Writing PIDs in csv and making screenshots..."
#------------------------------------------------------------------------------------
$lastSecond = (Get-Date).Second
while($true) 
{
    $currentSecond = (Get-Date).Second
    if ($currentSecond -ne $lastSecond) 
    {
        doAffinityAndSaveToCSV;       
        takeScreenshot;
        $lastSecond = $currentSecond
    }
    Start-Sleep -Milliseconds 200
}