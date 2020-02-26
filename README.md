PowerShell : comando para extraer lista de variables

 Get-ChildItem Env: | % {"export $($_.Name)=`"$($_.Value.Replace('\', '/').Replace('C:', '/mnt/c'))`""}
