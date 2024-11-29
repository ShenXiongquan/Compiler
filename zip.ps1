# 设置源文件夹路径
$sourceFolder = ".\src"  # 源文件夹路径

$defaultFileName = "output.zip"  # 默认压缩文件名
$fileName = Read-Host "请输入压缩文件名"

# 如果未输入文件名，使用默认值
if ([string]::IsNullOrWhiteSpace($fileName)) {
    $fileName = $defaultFileName
}

# 检查文件扩展名是否正确
if (![System.IO.Path]::HasExtension($fileName) -or [System.IO.Path]::GetExtension($fileName) -ne ".zip") {
    $fileName += ".zip"
}

# 确定目标压缩文件完整路径
$zipFilePath = Join-Path -Path (Get-Location) -ChildPath $fileName

# 检查目标压缩文件是否存在，如果存在则删除它
if (Test-Path $zipFilePath) {
    Remove-Item $zipFilePath -Force
}

# 压缩操作
try {
    # 加载 .NET 压缩库
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    [System.IO.Compression.ZipFile]::CreateFromDirectory($sourceFolder, $zipFilePath)
    # 成功提示信息
    $successMessage = "压缩完成，文件保存到：$zipFilePath"
    Write-Host $successMessage -ForegroundColor Green
} catch {
    # 失败提示信息
    $errorMessage = "压缩失败：$($_.Exception.Message)"
    Write-Host $errorMessage -ForegroundColor Red
}
