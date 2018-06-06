  $(function () {
		$('#stackedChartLocationId').change(function() {
		   loadResourceAndGetStackedChartGraphData();
		});

		$('#stackedChartResourceId').change(function() {
			getStackedChartGraphData($("#stackedChartLocationId").val(),$("#stackedChartResourceId").val());
		});
		//pieChart
		$('#pieChartLocationId').change(function() {
				getPieChartGraphData();
		});

		g_calendarObject = new JsDatePick({
			useMode : 2,
			target : "date",
			dateFormat : "%M/%d/%Y"
		});
		g_calendarObject.addOnSelectedDelegate(function(){
			getPieChartGraphData();
		});	

		//GAUGES
		$('#gaugeChartLocationId').change(function() {
			getGaugageChartGraphData();
		});

		startDate_calendarObject = new JsDatePick({
			useMode : 2,
			target : "startDate",
			dateFormat : "%M/%d/%Y"
		});
		startDate_calendarObject.addOnSelectedDelegate(function(){
			getGaugageChartGraphData();
		});


		endDate_calendarObject = new JsDatePick({
			useMode : 2,
			target : "endDate",
			dateFormat : "%M/%d/%Y"
		});
		endDate_calendarObject.addOnSelectedDelegate(function(){
			getGaugageChartGraphData();
		});
		
		getGaugageChartGraphData();
		loadResourceAndGetStackedChartGraphData();
		getPieChartGraphData();		
  });

	//***************************************************   Stacked Chart Related Functionality Starts  ****************************
	function loadResourceAndGetStackedChartGraphData(){
		try{
		    var stackedChartLocationId = $("#stackedChartLocationId").val();
			var url = "getResourceListByLocationId.html?locationId="+stackedChartLocationId;
			 
			$.get( url, function( data ) {	
			
				if(data!="" && data!=null && data!=undefined){
					var htmldata = "<option value='-1'> ALL </option>";
					$.each($.parseJSON(data), function(index, item) {
						htmldata = htmldata + "<option value='"+item.resourceId+"'>"+item.prefix + " " + item.firstName + "  " + item.lastName +"</option>";
					});
					$("#stackedChartResourceId").html(htmldata);
				}
			});			
			getStackedChartGraphData(stackedChartLocationId,"-1");
	   }catch(e){
		   alert("Error : "+e);
	   }
	}

	function getStackedChartGraphData(locationId,resourceId){
		if(locationId!=null && locationId!=undefined && resourceId!=null && resourceId!=undefined){
			var stackedChartType = getStackedChartType();				
			var graphsURL = "getStackedChart.html?locationId="+locationId+"&resourceId="+resourceId+"&stackedChartType="+stackedChartType;
			
			$.get( graphsURL, function( data ) {
				var json = $.parseJSON(data);
				displayStackedChart(json.stackedChartDays,json.noOfApptsBooked,json.noOfApptsOpened,json.noOfConfirmedNotifications,json.noOfUnConfirmedNotifications);
			});
		}
	}

	function displayStackedChart(stackedChartDaysString,noOfApptsBooked,noOfApptsOpened,noOfConfirmedNotifications,noOfUnConfirmedNotifications){
		var funding_based_scheduler = $("#funding_based_scheduler").val();

		if(funding_based_scheduler=='N' || funding_based_scheduler=='n'){
			drawStackedChart(stackedChartDaysString,noOfApptsBooked,noOfApptsOpened,noOfConfirmedNotifications,noOfUnConfirmedNotifications);
		}else{
			drawStackedChartRotatedLabels(stackedChartDaysString,noOfApptsBooked,noOfApptsOpened);
		}	
	}

	function getStackedChartType(){
		var stackedChartType = "";
		var funding_based_scheduler = $("#funding_based_scheduler").val();
		if(funding_based_scheduler=='N' || funding_based_scheduler=='n'){
			 stackedChartType = "normal";
		}else{
			 stackedChartType = "rotated";
		}
		return stackedChartType;
	}

	function prepareArray(str){
		var temp= new Array();
		temp = str.split(",");
		for (a in temp ) {
			temp[a] = parseInt(temp[a], 10);
		}
		return temp;
	}


	function drawStackedChart(stackedChartDaysString,noOfApptsBooked,noOfApptsOpened,noOfConfirmedNotifications,noOfUnConfirmedNotifications){
	
		applyHighChartsTheams();
	
	    // Apply the theme
       var highchartsOptions = Highcharts.setOptions(Highcharts.theme);

		$('#stackedcharts').highcharts({
    
            chart: {
                type: 'column'
            },    
            title: {
                text: 'Total Appointments'
            },
            subtitle: {
                text: '(in Time Blocks)'
            },
            xAxis: {
                //categories: ['10/04/2014', '11/04/2014', '14/04/2014', '15/04/2014', '16/04/2014']stackedChartDaysString 
				categories: stackedChartDaysString 
            },  
            yAxis: {
                allowDecimals: false,
                min: 0,
                title: {
                    text: 'Number of Time Blocks'
                }
            },    
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
						//'<b>'+  this.series.stack  +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        'Total: '+ this.point.stackTotal;
                }
            },    
            plotOptions: {
                column: {
                    stacking: 'normal'
                }
            },    
            series: [{
                name: 'Open ',
                //data: [3, 4, 4, 2, 5],
				data: noOfApptsOpened ,   
				color: '#ED561B',
                stack: 'Appointment'
            }, {
                name: 'Booked ',
                //data: [5, 3, 4, 7, 2],
				data: noOfApptsBooked ,
				color: '#50B432',
                stack: 'Appointment'
            }, {
                name: 'Confirmed ',
                //data: [3, 4, 4, 2, 5],
				data: noOfConfirmedNotifications ,
				color: '#24CBE5',
                stack: 'Notification'
            }, {
                name: 'UnConfirmed ',
                //data: [2, 5, 6, 2, 1],
				data: noOfUnConfirmedNotifications ,
				color: '#FFF263',
                stack: 'Notification'
            }]
        });
	}

function drawStackedChartRotatedLabels(stackedChartDaysString,noOfApptsBooked,noOfApptsOpened){	
	
		applyHighChartsTheams();
	    // Apply the theme
       var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
	
		stackedChartDaysString = stackedChartDaysString.split(",");
		noOfApptsBooked = prepareArray(noOfApptsBooked);
		noOfApptsOpened = prepareArray(noOfApptsOpened);

		$('#stackedcharts').highcharts({
    
            chart: {
                type: 'column'
            },    
            title: {
                text: 'Total Appointments'
            },
            subtitle: {
                text: '(in Time Blocks)'
            },
            xAxis: {
				categories: stackedChartDaysString ,
				type: 'category',
                labels: {
                    rotation: -45,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Times New Roman'
                    }
                }
            },  
            yAxis: {
                allowDecimals: false,
                min: 0,
                title: {
                    text: 'Number of Time Blocks'
                }
            },    
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        'Total: '+ this.point.stackTotal;
                }
            },    
            plotOptions: {
                column: {
                    stacking: 'normal'
                }
            },    
            series: [{
                name: 'Open ',
				data: noOfApptsOpened ,
				color: '#ED561B',
                stack: 'Appointment'
            }, {
                name: 'Booked ',
				data: noOfApptsBooked ,
				color: '#50B432',
                stack: 'Appointment'
            }]
        });
	}



	//***************************************************   Stacked Chart Related Functionality Ends  ****************************

function applyHighChartsTheams(){
	Highcharts.theme = {
		   //colors: ['#50B432', '#ED561B', '#50B432', '#ED561B', '#24CBE5', '#DDDF00'],
		   colors: ['#00CED1','#00FA9A','#00FFFF','#4682B4','#66CDAA','#778899','#7FFFD4','#87CEEB','#8FBC8F','#A0522D','#A9A9A9','#B0C4DE','#BC8F8F','#C0C0C0','#D2B48C','#DCDCDC','#EEE8AA','#F5DEB3','#FAEBD7','#FF7F50','#FFDEAD','#996633','#33CCCC','#6699FF','#FF99CC','#FF6600'],
		   chart: {
			  backgroundColor: {
				 linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
				 stops: [
					[0, 'rgb(255, 255, 255)'],
					[1, 'rgb(240, 240, 255)']
				 ]
			  },
			  borderWidth: 2,
			  plotBackgroundColor: 'rgba(255, 255, 255, .9)',
			  plotShadow: true,
			  plotBorderWidth: 1
		   },
		   title: {
			  style: {
				 color: '#000',
				 font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
			  }
		   },
		   subtitle: {
			  style: {
				 color: '#666666',
				 font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
			  }
		   },
		   xAxis: {
			  gridLineWidth: 1,
			  lineColor: '#000',
			  tickColor: '#000',
			  labels: {
				 style: {
					color: '#000',
					font: '11px Trebuchet MS, Verdana, sans-serif'
				 }
			  },
			  title: {
				 style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'

				 }
			  }
		   },
		   yAxis: {
			  minorTickInterval: 'auto',
			  lineColor: '#000',
			  lineWidth: 1,
			  tickWidth: 1,
			  tickColor: '#000',
			  labels: {
				 style: {
					color: '#000',
					font: '11px Trebuchet MS, Verdana, sans-serif'
				 }
			  },
			  title: {
				 style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'
				 }
			  }
		   },
		   legend: {
			  itemStyle: {
				 font: '9pt Trebuchet MS, Verdana, sans-serif',
				 color: 'black'

			  },
			  itemHoverStyle: {
				 color: '#039'
			  },
			  itemHiddenStyle: {
				 color: 'gray'
			  }
		   },
		   labels: {
			  style: {
				 color: '#99b'
			  }
		   },

		   navigation: {
			  buttonOptions: {
				 theme: {
					stroke: '#CCCCCC'
				 }
			  }
		   }
		};
}

	//***************************************************   Pie Chart Related Functionality Starts  ****************************

	function getPieChartGraphData(){
			var sucessCount = validateDateField("date","dateError","* Date");
			if(sucessCount>0){
				var graphsURL = "getPieChart.html?locationId="+$("#pieChartLocationId").val()+"&selectedDate="+$("#date").val();
				
				$.get( graphsURL, function( data ) {
					var json = $.parseJSON(data);
					drawPieChart(json.pieChartDate,json.resources,json.noOfConfirmedAppts);
				});
			}
	}

	function drawPieChart(pieChartDayString,resources,noOfConfirmedAppointments){
			applyHighChartsTheams();

			// Apply the theme
			var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
			
			// Build the data arrays
			var appointmentData = [];
			for (var i = 0; i < resources.length; i++) {    
				appointmentData.push( [ resources[i], noOfConfirmedAppointments[i] ] );  
			}
	
		 $('#piecharts').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
				},
				title: {
					text: "Appointment Results <br/> <p style='font-size:100%;'> Appointment Date: "+pieChartDayString +" </p>"
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: false,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							color: '#000000',
							connectorColor: '#000000',
							format: '<b>{point.name}</b>: {point.percentage:.2f} %'
						}
					}
				},
				series: [{
					type: 'pie',
					name: 'Confirmed Appointments',
					data: appointmentData
				}]
		});

	}

	//***************************************************   Pie Chart Related Functionality Ends  ****************************

	//***************************************************   Gauge Chart Related Functionality Starts   ****************************

	function getGaugageChartGraphData(){
		var sucessCount = validateDateField("startDate","startDateError","* Start Date");
		sucessCount = sucessCount + validateDateField("endDate","endDateError","* End Date");
		
		if(sucessCount==2){
			if(comparedates($('#endDate').val(),$('#startDate').val())>=0){
				var graphsURL = "getGaugeChart.html?locationId="+$("#gaugeChartLocationId").val()
					+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val();
				
				$.get( graphsURL, function( data ) {
					var json = $.parseJSON(data);
					drawGaugeGraph(json.gaugaeOpenedAppts,json.gaugaeBookedAppts);
				});
			}else{
				$('#endDateError').html("* End date should be greater than or equal to Start date.");
			}
		}
	}

	function validateDateField(fieldId,errorDivId,messageField){
		try{
			var inputvalue = $('#'+fieldId).val();
			if(inputvalue=='')  {
					$('#'+errorDivId).text(messageField +" is required.");
					return 0;
			}else{
					inputvalue = inputvalue.replace("/","-");
					inputvalue = inputvalue.replace("/","-");
					var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
					var sucess= pattern.test(inputvalue);
					if(sucess){
						$('#'+errorDivId).text("");
						return 1;
					}else{
						$('#'+errorDivId).html(messageField +"  should be in MM/dd/yyyy format");
						return 0;
					}
			}
		}catch(e){
			//alert("Exception  : "+e);
		}
	}


	function comparedates(dateStr,dateStr1) {	
		
		var datearray = dateStr.split("/");
		var date = datearray[1];
		//var month = getIndex(datearray[1]);
		var month = datearray[0]
		var year = datearray[2];
		
		var datearray1 = dateStr1.split("/");
		var date1 = datearray1[1];
		//var month1 = getIndex(datearray1[1]);
		var month1 = datearray1[0];
		var year1 = datearray1[2];
		
		if(year>year1){
			//alert('Year is greater');
			return 1;
		} else if(year<year1){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			if(month>month1){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<month1){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>date1){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<date1){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}


	function drawGaugeGraph(gaugaeOpenedAppts,gaugaeBookedAppts){	
		
		applyHighChartsTheams();
	    // Apply the theme
       var highchartsOptions = Highcharts.setOptions(Highcharts.theme);

		var gaugeOptions = {
	
				chart: {
					type: 'solidgauge'
				},
				
				title: null,
				
				pane: {
					center: ['50%', '85%'],
					size: '140%',
					startAngle: -90,
					endAngle: 90,
					background: {
						backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
						innerRadius: '60%',
						outerRadius: '100%',
						shape: 'arc'
					}
				},

				tooltip: {
					enabled: false
				},
				   
				// the value axis
				yAxis: {
					stops: [
						[0.1, '#55BF3B'], // green
						[0.5, '#DDDF0D'], // yellow
						[0.9, '#DF5353'] // red
					],
					lineWidth: 0,
					minorTickInterval: null,
					tickPixelInterval: 400,
					tickWidth: 0,
					title: {
						y: -70
					},
					labels: {
						y: 16
					}        
				},
				
				plotOptions: {
					solidgauge: {
						dataLabels: {
							y: 5,
							borderWidth: 0,
							useHTML: true
						}
					}
				}
			};

		var totalTimeSlots  = parseInt(gaugaeBookedAppts)+parseInt(gaugaeOpenedAppts);

		if(isNaN(gaugaeOpenedAppts)){
			gaugaeOpenedAppts = 0;
		}
		if(isNaN(gaugaeBookedAppts)){
			gaugaeBookedAppts = 0;
		}
		if(isNaN(totalTimeSlots)){
			totalTimeSlots = 250;
		}

			// The Booked Appointments gauge
			$('#booked_appts_container').highcharts(Highcharts.merge(gaugeOptions, {
				yAxis: {
					min: 0,
					max: totalTimeSlots,
					title: {
						text: 'Booked Appointments'
					},
					subtitle: {
						text: '(Appointments)'
					}       
				},

				credits: {
					enabled: false
				},
			
				series: [{
					name: 'Booked Appointments',
					//data: [80],
					data: gaugaeBookedAppts,
					dataLabels: {
						format: '<div style="text-align:center"><span style="font-size:25px;color:' + 
							((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' + 
							'<span style="font-size:12px;color:silver">Appointments</span></div>'
					},
					tooltip: {
						valueSuffix: 'Booked Appointments'
					}
				}]
			
			}));
			
			
			// The Open Appointments gauge
			$('#opened_appts_container').highcharts(Highcharts.merge(gaugeOptions, {
				yAxis: {
					min: 0,
					max: totalTimeSlots,
					title: {
					   text: 'Open Appointments'
					} ,
					subtitle: {
						text: '(Appointments)'
					}       
				},
			
				series: [{
					name: 'Open Appointments',
					//data: [1],
					data: gaugaeOpenedAppts,
					dataLabels: {
						format: '<div style="text-align:center"><span style="font-size:25px;color:' + 
							((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' + 
							'<span style="font-size:12px;color:silver">Appointments</span></div>'
					},
					tooltip: {
						valueSuffix: 'Open Appointments'
					}      
				}]
			
			}));
			
	}

	function periodicallyDrawGaugeGraph(){
			// Bring life to the dials
			setInterval(function () {
				getGaugageChartGraphData();
				var gaugaeOpenedAppts = prepareArray($('#gaugaeOpenedAppts').val());
				var gaugaeBookedAppts = prepareArray($('#gaugaeBookedAppts').val());
				
				if(isNaN(gaugaeOpenedAppts)){
					gaugaeOpenedAppts = 0;
				}
				if(isNaN(gaugaeBookedAppts)){
					gaugaeBookedAppts = 0;
				}

				// Booked Appointments
				var chart = $('#booked_appts_container').highcharts();
				if (chart) {
					var point = chart.series[0].points[0];
					point.update(gaugaeBookedAppts);
				}
				
				// Open Appointments
				chart = $('#opened_appts_container').highcharts();
				if (chart) {
					var point = chart.series[0].points[0];
					point.update(gaugaeOpenedAppts);
				}
				
			}, 60000); 
	}

		//***************************************************   Gauge Chart Related Functionality Ends   ****************************