<!-- 
function newXMLHttpRequest()
{
	var xmlreq = false;

	if (window.XMLHttpRequest)
	{
		// Create XMLHttpRequest object in non-Microsoft browsers
		xmlreq = new XMLHttpRequest();
	}
	else if (window.ActiveXObject)
	{
		// Create XMLHttpRequest via MS ActiveX
		try
		{
			// Try to create XMLHttpRequest in later versions of Internet Explorer
			xmlreq = new ActiveXObject("Msxml2.XMLHTTP"); 
		}
		catch (e1)
		{
			// Failed to create required ActiveXObject
			try
			{
				// Try version supported by older versions of Internet Explorer
				xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e2)
			{
				// Unable to create an XMLHttpRequest with ActiveX
			}
		}
	}
	return xmlreq;
}

function callback(req, restype, xmlHandler)
{
	return function()
	{
		if (req.readyState == 4)
		{
			if (req.status == 200) xmlHandler(((restype == "TEXT") ? req.responseText : req.responseXML));
		}
	}
}

function sendXMLHttpRequest(req, fn, path, params, reqtype, contenttype, cache)
{
	try
	{
		req.onreadystatechange = fn;
		req.open(((reqtype) ? reqtype : "POST"), ((path) ? path : ""), true);
		req.setRequestHeader("Content-Type", ((contenttype) ? contenttype : "application/x-www-form-urlencoded"));
		req.setRequestHeader("Cache-Control", ((cache) ? cache : "no-cache"));
		req.send(((params) ? params : null));
	}
	catch(e)
	{
		var msg = (typeof(e) == "string") ? e : ((e.message) ? e.message : "Unknown Error");
		alert("Unable to get XML data:\n" + msg);
		return;
	}
}
// -->
