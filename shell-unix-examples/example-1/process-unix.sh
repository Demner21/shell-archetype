ruta="/tmp/test-unix"

. $ruta/bin/.passet
rm $ruta/log/*log
rm $ruta/data/*.dat

declare -a List=(
"SYSDATE-52"
"SYSDATE-51"
)

for FILE_PROCESSING in "${List[@]}"
do
echo "processing FILE_PROCESSING: ${FILE_PROCESSING}"
echo "-------------------"
echo $FILE_PROCESSING
echo "Trayendo data para reproceso " >> $ruta/log/PROCESS.log
sqlplus -S $USRDB/$PASSDB@"$CONECTION_COMPLETE_DB" <<EOF > $ruta/data/$FILE_PROCESSING @$ruta/sql/call_query_bd.sql $FILE_PROCESSING
EOF
done
echo " Se finaliza de extraer data para $FILE_PROCESSING" >> $ruta/log/PROCESS.log

cat $ruta/log/PROCESS.log
for FILE_PROCESSING in "${List[@]}"
do  
    #quitando espacios innecesarios
    sed  -i 's/\s*,\s*/,/g' $ruta/data/$FILE_PROCESSING

    #eliminando las primeras 12 lineas
    #sed -i '1,12d'  $ruta/data/$FILE_PROCESSING

    #eliminando las ultimas 3 lineas
    #sed -i "$(( $(wc -l <$ruta/data/$FILE_PROCESSING)-3+1 )),$ d" $ruta/data/$FILE_PROCESSING 
done


echo " ejemplo de query en el mismo lugar de invocación"
sqlplus -S $USRDB/$PASSDB@"$CONECTION_COMPLETE_DB" <<EOF > $ruta/data/$FILE_PROCESSING
SET pagesize 0
SET linesize 500
SET feedback off
SET termout off
SET heading off
SET head off
SET verify off
set echo off
set serveroutput off

    SELECT NAME_PARTNER,
        ID_PARTNER
    FROM TABLE_PARTNER;
EXIT
EOF

echo " ejemplo de llamado a store procedure"
RETVAL=`sqlplus -S $USRDB/$PASSDB@"$CONECTION_COMPLETE_DB"<<FIN 
SET SERVEROUTPUT ON 
SET SERVEROUTPUT ON FORMAT WORD_WRAPPED
DECLARE
P_cod_resultado VARCHAR2(10);
P_msg_resultado VARCHAR2(50);
BEGIN
	  MY_OWNER.MY_PKG_EXAMPLE.MY_SP_EXAMPLE(P_cod_resultado,P_msg_resultado);
	  dbms_output.put_line('respuesta de store procedure '|| P_cod_resultado ||' '|| P_msg_resultado);
END;
/
EXIT
FIN
`
k_cod_resultado=""
k_msg_resultado=""

k_cod_resultado=`echo $RETVAL | grep responseSP | awk '{print $2}'`
k_msg_resultado=`echo $RETVAL | grep responseSP | awk '{print $3}'`
echo "COD_RESPUESTA: $k_cod_resultado" >> $ruta/log/PROCESS.log
echo "MSG_RESPUESTA: $k_msg_resultado" >> $ruta/log/PROCESS.log

echo " ejemplo de sqlldr"
export PATH=$PATH:~/databse-utils/instantclient_12_2

sqlldr $CONECTION_COMPLETE_DB_SQL_LDR control=$ruta/ctl/load_data.ctl data=$ruta/data/test_data log=$ruta/log/PROCESS_SQL_LDR.log
