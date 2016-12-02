采集程序

1、配置
	config文件夹中的
	app-sysconfig.xml配置了系统相关内容；
	app-database.xml配置了数据库信息；
	config.properties中配置四项：数据库database info，代理proxy info，文件存储file store，运行方式run style。
	journal.txt中配置web science的期刊采集，需要期刊名和年份，格式参考journal.txt
	
	config/site文件夹是站点整体配置。
	
	site文件夹中是站点结构化数据抽取的xpath配置。
	config/site文件夹中的app-*.xml和site文件夹中的*.xml的*必须完全相同，如config/site/app-test.xml和site/test.xml

2、程序
	WOSStart类作为程序入口，可选后台和测试的方式运行
	
3、部署
	创建wos.jar包后，创建start.sh文件，内容为java -Xmx1200M -jar wos.jar
	

该采集程序采集web of science上的期刊数据或含有搜索的关键词数据，格式如下：
	采集检索的期刊数据：期刊名:起始年:终止年:标识
	Transactions of the Institute of Measurement and Control:2013:2013:SO
	采集检索的关键词数据：搜索词:起始年:终止年:标识
	core:1900:2014:TS
***
Booleans: AND, OR, NOT, SAME, NEAR
Field Tags:
TS= Topic 
TI= Title 
AU= Author [Index] 
AI= Author Identifiers 
GP= Group Author [Index] 
ED= Editor 
SO= Publication Name [Index] 
DO= DOI 
PY= Year Published 
CF= Conference 
AD= Address 
OG= Organization-Enhanced [Index] 
OO= Organization 
SG= Suborganization 
SA= Street Address 
CI= City 
PS= Province/State 
CU= Country 
ZP= Zip/Postal Code 
FO= Funding Agency 
FG= Grant Number 
FT= Funding Text 
SU= Research Area 
WC= Web of Science Category 
IS= ISSN/ISBN 
UT= Accession Number 
PMID= PubMed ID 
	
	