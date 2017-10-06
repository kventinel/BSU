dat<-read.table("f")
dat
plot(dat,type="p",main="Диаграмма рассеяния",xlab="X", ylab="Y")
cl1<-kmeans(dat,2)
table(cl1$cluster)
cl1$centers
plot(dat,col=ifelse(cl1$cluster==1,"blue","green"))
legend("topleft",legend=c("1","2"),fill=c("blue","green"))
plot(dat,pch=ifelse(cl1$cluster==1,1,2))
legend("topleft",legend=c("1","2"),pch=c(1,2))
cl2<-kmeans(dat,3)
table(cl2$cluster)
cl2$centers
plot(dat,col=ifelse(cl2$cluster==1,"blue", ifelse(cl2$cluster==2, "green", "red")))
legend("topleft",legend=c("1","2","3"),fill=c("blue","green", "red"))
plot(dat,pch=ifelse(cl2$cluster==1,1,ifelse(cl2$cluster==2,2,3)))
legend("topleft",legend=c("1","2","3"),pch=c(1,2,3))

