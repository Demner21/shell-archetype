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