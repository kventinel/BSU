dat=read.table("f.txt")
dat
plot(dat,type="p",main="Корреляционное поле",xlab="X", ylab="Y")
x<-as.numeric(dat[,1])
y<-as.numeric(dat[,2])
sr<-mean(x)
otkl<-sqrt(var(x))
matr2<-rep(0,3*4)
dim(matr2)<- c(3,4)
for (i in 1:3) {
  matr2[i,1]<-paste('(',as.character(sr-i*otkl),',',as.character(sr+i*otkl),')')
  matr2[i,2]<-length(x[x>(sr-i*otkl) & x<(sr+i*otkl)])
  matr2[i,3]<-length(x[x>(sr-i*otkl) & x<(sr+i*otkl)])*100/length(x)
}
matr2[1,4]<-68.3
matr2[2,4]<-95.4
matr2[3,4]<-99.7
colnames(matr2)<-c("Интервалы значений признака-фактора",
                   "Число единиц, входящих в интервал",
                   "Удельный вес единиц, входящих в интервал, в общем их числе, %",
                   "Удельный вес единиц, входящих в интервал, при нормальном распределении, %")
matr2
n<-round(1+log2(length(x)), 0)
razmah<-max(x)-min(x)
interval<-razmah/n
matr3<-rep(0,n*4)
dim(matr3)<- c(n,4)
for(i in 1:n-1){
  matr3[i,1]<-paste('[',as.character(min(x)+(i-1)*interval),',',as.character(min(x)+i*interval),')')
  matr3[i,2]<-length(x[x>=(min(x)+(i-1)*interval) & x<(min(x)+i*interval)])
  matr3[i,3]<-sum(y[x>=(min(x)+(i-1)*interval) & x<(min(x)+i*interval)])
  matr3[i,4]<-sum(y[x>=(min(x)+(i-1)*interval) & x<(min(x)+i*interval)])/length(x[x>=(min(x)+(i-1)*interval) & x<(min(x)+i*interval)])
}
matr3[n,1]<-paste('[',as.character(max(x)-interval),',',as.character(max(x)),']')
matr3[n,2]<-length(x[x>=(max(x)-interval) & x<=max(x)])
matr3[n,3]<-sum(y[x>=(max(x)-interval) & x<=max(x)])
matr3[n,4]<-sum(y[x>=(max(x)-interval) & x<=max(x)])/length(x[x>=(max(x)-interval) & x<=max(x)])
colnames(matr3)<-c("Интервалы",
                   "Число вариант в интервале",
                   "Cумма результ фактора",
                   "Среднее значение результ фактора")
matr3
koeff_corel<-cor(x,y)
trash<-abs(koeff_corel)*sqrt((length(x)-2)/(1-koeff_corel*koeff_corel))
ttabl<-2.587
ifelse(trash>ttabl, print("Между признаками х и у существует зависимость"),
       print("Между признаками х и у зависимости нет"))
b<-(koeff_corel*sqrt(var(y)))/sqrt(var(x))
a<-mean(y)-(b*mean(x))
abline(lm(y~x))
