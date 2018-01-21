library(MASS)
x<-c(rnorm(20,mean=0,sd=1),rnorm(10,mean=3,sd=1))
y<-c(rnorm(20,mean=0,sd=1),rnorm(10,mean=3,sd=1))
xy<-cbind(x,y)
n<-30
n.train<-n*0.7
n.test<-n*0.3
n.bad<-floor(n.train*0.2)


cl<-kmeans(xy,2)
idx.train<-sample(1:n,n.train)
idx.test<-(1:n)[!(1:n %in% idx.train)]
data.train<-xy[idx.train,]
data.test<-xy[idx.test,] 
cl.train<-cl$cluster[idx.train]
cl.test<-cl$cluster[idx.test] 
mod<-qda(data.train,cl.train)
cl.test_est<-predict(mod,data.test)$class
sum(cl.test_est!=cl.test)/n.test
idx<-idx.test[cl.test_est!=cl.test]
plot(xy,col=ifelse(cl$cluster==1,"blue","green"))
legend("topleft",legend=c("1","2"),fill=c("blue","green"))
points(xy[idx,],col="red")
points(xy[idx.train,],pch=2)


idx.bad<-sample(1:n.train,n.bad)
for(i in 1:n.bad) {
  idx.bad[i]=idx.train[idx.bad[i]]
  if(cl$cluster[idx.bad[i]]==1) cl$cluster[idx.bad[i]]=2 else cl$cluster[idx.bad[i]]=1
}
data.train<-xy[idx.train,]
data.test<-xy[idx.test,]
cl.train<-cl$cluster[idx.train]
cl.test<-cl$cluster[idx.test] 
mod<-qda(data.train,cl.train)
cl.test_est<-predict(mod,data.test)$class
sum(cl.test_est!=cl.test)/n.test
idx<-idx.test[cl.test_est!=cl.test]
plot(xy,col=ifelse(cl$cluster==1,"blue","green"))
legend("topleft",legend=c("1","2"),fill=c("blue","green"))
points(xy[idx,],col="red")
points(xy[idx.train,],pch=2)
points(xy[idx.bad,],pch=3)