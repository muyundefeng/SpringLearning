#! /bin/bash

echo "please enter your chooice"
echo "j - please choose the source java code file"
strigger=0
while [[ $strigger -eq 0 ]]; do
	read -p "input a val:"  val
	echo "your enter the chooice is  $val"
	case "$val" in
		j)
			echo "please enter your java source  code path"
			read -p "input your source code path:" path1
			cd  $path1
			echo `pwd`
			count=0
			for i in  `ls |grep "java$"`; do
				let count=count+1
				#statements
			done
			#count=1
			echo "count:$count"
			if [[ $count -eq 0 ]]; then
				echo "there is no source code java file"
				#statements
			elif [[ $count -eq 1 ]]; then
				#`cd  $path`
				#echo `pwd`
				target=`ls |grep "java$"`
				echo "your compile java source  code is $target"
				echo "staring compiling the source code file"
				javac $target
				if [[ -f `ls|grep ".class$"` ]]; then
					echo "success complie the sorce code"
					exefile=`ls |grep ".class$"`
					echo "execute file is $exefile"
					ecefiler=${exefile%.*}
					echo "filename is $ecefiler"
					java $ecefiler

				else
					echo "no class file .please try again"
					#statements
					#statements
				fi
				#statements
			fi
		;;
		*)
			echo "not this chooice"

 	esac
done

