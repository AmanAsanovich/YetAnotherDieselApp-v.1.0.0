<# To set up
new application
in Task Sheduler 
#>
Set-ExecutionPolicy RemoteSigned -force -Scope CurrentUser
$T = New-JobTrigger -AtStartup -RepetitionInterval (New-TimeSpan -Hours 1) -RepeatIndefinitely -Verbose
$Cred = My-PC\Me
Register-ScheduledJob -Name "DieselUp" -FilePath C:\Users\...\DieselApp\runDiesel.ps1 -Trigger $T -Credential $Cred
