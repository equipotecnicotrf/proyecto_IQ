#!/bin/bash

. ./build.env
. ./docker.env

DOCKER_PATH=$PWD

clear

function _help()
{
  echo "Script to execute common docker functions for $DOCKER_CONTAINER_NAME"
  echo ""
  echo "Usage: ./docker.sh [OPTION]"
  echo ""
  echo "Enable options: "
  echo ""
  echo " build"
  echo " run" 
  echo " start"
  echo " stop"
  echo " restart"
  echo " logs"
  echo " conn"
  echo " status"
  echo " drop"
  echo " dropImage"
  echo ""
}

function _build(){
	echo "Execute build image $DOCKER_IMAGE_NAME..."
	echo "Drop $DOCKER_CONTAINER_NAME..."
	docker rm -f $DOCKER_CONTAINER_NAME 1>&2
	echo "Drop image $DOCKER_IMAGE_NAME..."
	docker rmi $DOCKER_IMAGE_NAME 1>&2
	docker build -t $DOCKER_IMAGE_NAME --build-arg JAR_NAME=$JAR_NAME . 1>&2
}

function _run(){
	echo "Execute run $DOCKER_CONTAINER_NAME..."
	docker run -d --name $DOCKER_CONTAINER_NAME -e PROFILE=$PROFILE -e NAME_LOG_FILE=$NAME_LOG_FILE -v "$DOCKER_PATH/logs:/logs" -v "$DOCKER_PATH/config:/config" -p $WS_PORT:$WS_PORT $DOCKER_IMAGE_NAME 1>&2
}

function _start(){
	echo "Execute start $DOCKER_CONTAINER_NAME..."
	docker start $DOCKER_CONTAINER_NAME 1>&2
}

function _stop(){
	echo "Execute stop $DOCKER_CONTAINER_NAME..."
	docker stop $DOCKER_CONTAINER_NAME 1>&2
}

function _drop(){
	echo "Execute drop $DOCKER_CONTAINER_NAME..."
	docker rm -f $DOCKER_CONTAINER_NAME 1>&2
}

function _dropImage(){
	echo "Execute drop image $DOCKER_IMAGE_NAME..."
	docker rmi -f $DOCKER_IMAGE_NAME 1>&2
}

function _logs(){
	docker logs $DOCKER_CONTAINER_NAME 1>&2
}

function _conn(){
	echo "Connect to $DOCKER_CONTAINER_NAME..."
	docker exec -it $DOCKER_CONTAINER_NAME /bin/sh 1>&2
}

function _status(){
	echo "Status for $DOCKER_CONTAINER_NAME..."
    docker ps -f name=$DOCKER_CONTAINER_NAME 1>&2
}

case  "$1" in
	build)
		_build
		;;
	
	run)
        _run
        ;;

    start)
    	_start
        ;;

    stop)
        _stop
        ;;

    restart)
    	echo "Execute restart $DOCKER_CONTAINER_NAME..."
        _stop
        _start
        ;;

    conn)
    	_conn
        ;;

    status)
    	_status
         ;;
         
    logs)
    	_logs
    	;;
    	
    drop)
    	_drop
    	;;
    	
    dropImage)
    	_dropImage
    	;;
    	
    -h | --help)
    	_help
    	;;
    
    *)
    	echo -e -n "Invalid option!!!\n"
    	echo -e -n "Use -h or --help for more information.\n"
    	
        exit 1
        ;;

esac

exit 0
            








