# crawler
##Intro
Crawl the infomations of apps from different channel.MyCrawler exposes the restful interface to the users. Users send the trigger infomations(e.g.taksId,channelId and so on),then the crawler can perform perfectly the task and response the apps infos to the user.
##Fetures
- based on the webmagic0.5.2
- uniform rest api
- concurrent crawling approach
- simpified models
- high-efficiency HTML parser JSOUP
增量式网络爬虫的实现的关键组件是保存已经访问过得url字符串，队列中的待访问的url队列也应该保存起来．
实现细节:（队列中以及访问过的url链接存放在redis数据库中）
1.给定一个爬虫的种子url链接，该url链接一般是网站的主页．在添加种子url链接之前首先要判断数据库中待访问队列中是否为空，若不为空，则恢复队列中的数据，这些作为起始种子url链接．如果数据库中的待访问队列中数据为空，则判定爬虫为新启动的，首页作为种子url链接．
2.恢复数据成功之后，要对新增加的url链接，时刻保存到redis数据库，包括待访问以及访问过的．
