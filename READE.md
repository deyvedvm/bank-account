    docker run -it -d --name mysql-cqrs -p 3306:3306 --network deyvedev -e MYSQL_ROOT_PASSWORD=bankRootPassword --restart always -v mysql_data_cqrs:/var/lib/mysql mysql

    docker run -it -d --name mongo-cqrs -p 27017:27017 --network deyvedev --restart always -v mongo_data_cqrs:/data/db mongo