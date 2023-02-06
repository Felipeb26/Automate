@REM @echo off

@REM curl -A "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64)" -L https://github.com/Felipeb26/Automate/blob/master/automate.exe?raw=true -o Automate.exe

@REM pause 5
set path=%LOCALAPPDATA%
@REM move "C:\Users\felipe.silva\ROBOS\Automate\Automate.exe" %path%

@REM echo "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"
@REM echo "\\\                            \\\"
@REM echo "\\\ Automate Dowload Complete! \\\"
@REM echo "\\\                            \\\"
@REM echo "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"

echo %path%

@REM pause 5

@REM XXMKLINK "C:\Users%username%\Desktop\Automate" "%path%\Automate.exe"

@REM echo oLink.arguments = Chr(34) & "%path%\Automate.exe" & Chr(34) > teste.Ink // roda o o arquivo mesmo modo

set SCRIPT = "%TEMP%\%RANDOM%-%RANDOM%-%RANDOM%-%RANDOM%.vbs"

echo %SCRIPT%

echo Set oWS = WScrip.CreateObject("WScript.Shell") >> %SCRIPT%
echo sLinkFile = %USERPROFILE%\Desktop\AUTOMATE.lNK >> %SCRIPT%
echo Set oLink = oWS.CreateShortCut(sLinkFile) >> %SCRIPT%
echo oLink.TargetPath = "C:\Users\felipe.silva\AppData\Local\Automate.exe" >> %SCRIPT%
echo oLink.Save >> %SCRIPT%

cscript /nologo %SCRIPT%
del %SCRIPT%

@REM C:\Users\felipe.silva\AppData\Roaming\Microsoft\Windows\Start Menu\Programs -> espaço onde salvar o Ink