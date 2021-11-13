#!/bin/ksh
NAME_SHELL="SH_TEST"
#*********************************************************************
#* DESCRIPCION      	: Invocacion de SH_TEST
#* FECHA                : 17/02/2020 	                                        
#* VERSION             	: 1.0	                                        
#************************************************************************
clear
#Setup VARSET:
RUTA_VARSET="C:\\gits\\$NAME_SHELL\\remote\\BIN\\.varset_windows"

. $RUTA_VARSET

LOG_NAME=$LOG_NAME_REQUEST
FECHA_LOG=`date +"%d%m%Y"`
TRANSACTION=`date +%Y%m%d%H%M%S`
FILELOG=$DIR_LOG\\${LOG_NAME}.log
LOGDATE=`date +"%d-%m-%Y %H:%M:%S"`
USR=`whoami`

export properties=$PROPERTIES_PATH
############### INICIO - EJECUCION DE SHELL ###############
echo "${LOGDATE} INFO  @ [INICIO de ejecucion de la Shell - $NAME_SHELL -][`date +%Y-%m-%d@%H:%M:%S`]"
############### 2 - EJECUCION DE JAR ###############
echo "${DATE} INFO  @ [INICIO del proceso][`date +%Y-%m-%d@%H:%M:%S`]"
echo "${DATE} INFO  @ Usuario: $USR "  
echo "${DATE} INFO  @ Transaccion: ${TRANSACTION}" 
echo "${DATE} INFO  @ Ruta del archivo properties: ${PROPERTIES_PATH}"  
echo "${DATE} INFO  @ [INICIO de la ejecucion de la Shell][`date +%Y-%m-%d@%H:%M:%S`]"
echo "${DATE} INFO  @ PARAMETROS A ENVIAR A:" 
echo "*****************************************************"
echo "${DATE} INFO  @ Transaccion: ${TRANSACTION}" 
echo "******************************************************"
echo ""$JAVA_PATH"java -jar ${HOME_REQUEST}/$NAME_SHELL.jar ${TRANSACTION}"
"$JAVA_PATH"java -jar ${HOME_REQUEST}/$NAME_SHELL.jar ${TRANSACTION} >> $FILELOG
DATE=`date +%Y-%m-%d@%H:%M:%S`
############### FIN - EJECUCION DE JAR ###############
echo "${LOGDATE} INFO  @ [FIN de ejecucion de la Shell - $NAME_SHELL -][`date +%Y-%m-%d@%H:%M:%S`]"  
echo "${DATE} INFO  @ Ruta del archivo log generado:${FILELOG}"  
echo "*************************************************************************************************************************************" 
exit
