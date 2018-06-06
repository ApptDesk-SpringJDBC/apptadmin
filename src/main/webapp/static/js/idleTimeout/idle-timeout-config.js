 $(document).ready(function (){
  $(document).idleTimeout({
	  redirectUrl: 'log-out.html',      // redirect to this url on logout. Set to "redirectUrl: false" to disable redirect

	  // idle settings
	  idleTimeLimit: 1500,           // 'No activity' time limit in seconds. 1200 = 20 Minutes
	  idleCheckHeartbeat: 1,       // Frequency to check for idle timeouts in seconds

	  // optional custom callback to perform before logout
	   customCallback: false,       // set to false for no customCallback
	   //customCallback:    function () {    // define optional custom js function
	   //  alert("perform custom action before logout");
	   //},

	  // configure which activity events to detect
	  // http://www.quirksmode.org/dom/events/
	  // https://developer.mozilla.org/en-US/docs/Web/Reference/Events
	  activityEvents: 'click keypress scroll wheel mousewheel mousemove', // separate each event with a space

	  // warning dialog box configuration
	  enableDialog: true,           // set to false for logout without warning dialog
	  dialogDisplayLimit: 120,       // 20 seconds for testing. Time to display the warning dialog before logout (and optional callback) in seconds. 180 = 3 Minutes
	  dialogTitle: 'Session Expiration Warning', // also displays on browser title bar
	  dialogText: 'Because you have been inactive, your session is about to expire.',
	  dialogTimeRemaining: 'Time remaining',
	  dialogStayLoggedInButton: 'Stay Logged In',
	  dialogLogOutNowButton: 'Log Out Now',

	  // error message if https://github.com/marcuswestin/store.js not enabled
	  errorAlertMessage: 'Please disable "Private Mode", or upgrade to a modern browser.',

	  // server-side session keep-alive timer
	  sessionKeepAliveTimer: 1700,   // ping the server at this interval in seconds. 600 = 10 Minutes. Set to false to disable pings
	  sessionKeepAliveUrl: "activateSession.html" // set URL to ping - does not apply if sessionKeepAliveTimer: false
   });
});