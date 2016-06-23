

var express = require('express');
var router = express.Router();
var moment = require('moment');
var mysql = require('mysql');

//connection 객체 생성
var connection = mysql.createConnection(
{
	host : 'localhost',
	port : 3306,
	user : 'root',
	password : '4678',
	database : 'hackday'
});

// connect to mysql
connection.connect(function(err)
{
	if(err)
	{
		console.error('mySQL connection error');
		console.error(err);
		throw err;
	}
	else
	{
		console.log('mySQL connect success');
	}
});

//DB route

router.get('/searchApi/name/:name', function(req,res,next)
{
	var name = req.params.name;
	connection.query('select * from user where JSON_EXTRACT(jsonforname,"$.name") like ? AND JSON_EXTRACT(jsonforname,"$.duplicate") =0;',["\""+name+"%"],function(err,result)
	{
		if(err)
		{
			console.error(err);
			//Must type return 
			return next(err);
		}
                res.send(result);
	});
});

router.get('/searchApi/job/:job', function(req,res,next)
{
	var job = req.params.job;

	connection.query('select * from user where JSON_EXTRACT(jsonforname,"$.job") = ? AND JSON_EXTRACT(jsonforname,"$.duplicate") =0;',[job],function(err,result)
	{
		if(err)
		{
			console.error(err);
			//Must type return 
			return next(err);
		}
                res.send(result);
	});
});


router.get('/searchApi/old/:old', function(req,res,next)
{
	var old = req.params.old;

	connection.query('select * from user where JSON_EXTRACT(jsonforname,"$.old") like ? AND JSON_EXTRACT(jsonforname,"$.duplicate") =0;',["\""+old+"세%"],function(err,result)
	{
		if(err)
		{
			console.error(err);
			//Must type return 
			return next(err);
		}
                res.send(result);
	});
});


router.get('/searchApi/job/:job', function(req,res,next)
{
	var job = req.params.job;

	connection.query('select * from user where JSON_EXTRACT(jsonforname,"$.job") = ? AND JSON_EXTRACT(jsonforname,"$.duplicate") =0;',[job],function(err,result)
	{
		if(err)
		{
			console.error(err);
			//Must type return 
			return next(err);
		}
                res.send(result);
	});
});


router.get('/searchApi/nameAndJob/:job', function(req,res,next)
{
	var job = req.params.job;

	connection.query('select * from user where JSON_EXTRACT(jsonforname,"$.job") = ? AND JSON_EXTRACT(jsonforname,"$.duplicate") =0;',[job],function(err,result)
	{
		if(err)
		{
			console.error(err);
			//Must type return 
			return next(err);
		}
                res.send(result);
	});
});

//When push 'Ctrl + c' disconnect mysql
process.on('SIGINT',function()
{
	connection.end(function()
	{
		console.log('mySQL connection close');
		process.exit(0);
	});
});

//connection 객체를 내보냄 ?
module.exports = router;