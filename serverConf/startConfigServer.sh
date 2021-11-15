apt-get update

apt-get install ipconfig

iptables -A INPUT -i lo -j ACCEPT
iptables -A OUTPUT -o lo -j ACCEPT
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -A INPUT -p tcp --dport 22 -j ACCEPT
iptables -A INPUT -p tcp --dport 443 -j ACCEPT
iptables -P INPUT DROP

apt-get install nginx
cp -u serverConf/nginx.conf /etc/nginx/nginx.conf
service nginx reload

apt-get install snapd
sudo snap install core; sudo snap refresh core
sudo snap install --classic certbot
sudo ln -s /snap/bin/certbot /usr/bin/certbot
sudo certbot --nginx
maksim.solovev.99@inbox.ru
yes
1


apt-get -y install postgresql
sudo su - postgres
psql -U postgres
\password
hotabmax
hotabmax
\q
\exit

java -jar serverConf/Project.jar &

