
while [ true ] ; do
while read -r line; 
do 
if test "$line" = "" 
then
	sleep 0
else
	echo -e "$line"
	sleep 1
fi
done < sample.txt
done
