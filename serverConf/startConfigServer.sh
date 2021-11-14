apt-get update

apt-get install ipconfig
iptables -A INPUT -i lo -j ACCEPT
iptables -A OUTPUT -o lo -j ACCEPT
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -P INPUT DROP

apt-get install nginx
cp -u startConfigServer/nginx.conf etc/nginx/nginx.conf
service nginx reload

apt-get -y install postgresql
psql
\password
hotabmax
hotabmax
\q

cd /conf
docker build -t hotabmax/project:v1 .
docker run -d hotabmax/project