dat<-read.table("f.txt")
dat
x<-as.numeric(dat[,1])
x
y<-as.numeric(dat[,2])
y
sr_y<-mean(y)
sr_y
R<-1
dat[,3]<-0 
for(i in 1:length(y))
  if((y[i]<sr_y)) dat[i,3] = 1 else dat[i,3] = 2
for(i in 2:length(y))
  if(y[i-1] != y[i]) R<-R+1
R
t<-1
t
if(((length(y)+1-t*sqrt(length(y)-1))/2<=R)&&((length(y)+1+t*sqrt(length(y)-1))/2>=R)) print("Тренда нет") 
if(((length(y)+1-t*sqrt(length(y)-1))/2>R)||((length(y)+1+t*sqrt(length(y)-1))/2<R)) print("Тренд есть")
dat[,4]<-0 
dat[1,4]=(5*dat[1,2]+2*dat[2,2]-dat[3,2])/6 
dat[length(y),4]=(5*dat[length(y),2]+2*dat[length(y)-1,2]-dat[length(y)-2,2])/6 
dat
length(y) 
for(i in 2:(length(y)-1) ) 
  dat[i,4]=(dat[i-1,2]+dat[i,2]+dat[i+1,2])/3 
dat[,5]<-0
dat
seredina<-length(y)/2
for(i in 1:length(y))
  dat[i,5]<--19+2*i
dat[,6]<-0
dat
for(i in 1:length(y))
  dat[i,6]<-dat[i,5]*dat[i,5]
dat[,7]<-0
dat
for(i in 1:length(y))
  dat[i,7]<-dat[i,2]*dat[i,5]
dat
a0<-sr_y
a1<-sum(dat[,7])/sum(dat[,6])
a1
a0
dat[,8]<-0
for(i in 1:length(y))
  dat[i,8]<-a0+a1*dat[i,5]
dat
next_year<-a0+a1*(dat[length(y),5]+2)
next_year
plot(dat[,1],dat[,2], col = "black", type = "l",main = "ряд динамики", ylab = "значения", xlab = "года")
lines(dat[,1],dat[,4], col = "magenta")
lines(dat[,1], dat[,8], col = "blue")
colnames(dat)<-c("год","значение","группа относительно среднего","сглаженный ряд", "t","t^2","y*t","сглаженный через значение ряд")

