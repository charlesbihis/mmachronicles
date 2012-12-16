<%@	page contentType="text/html;charset=UTF-8"
		 language="java"
		 import="java.io.FileInputStream"
		 import="java.io.IOException"
		 import="java.util.logging.Logger"
		 import="java.util.Properties"
%>

<%
	final Logger log = Logger.getLogger("veronica.web");
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		log.severe("Error loading properties file - " + e.getMessage());
		e.printStackTrace();
	}  // try-catch statement
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%= properties.getProperty("site.title") + " - " + properties.getProperty("site.slogan") %></title>
	<link media="all" type="text/css" rel="stylesheet" href="/css/all.css" />
	<script type="text/javascript" src="/js/main.js"></script>
	<!--[if lt IE 7]><link rel="stylesheet" type="text/css" href="css/lt7.css" media="screen"/><![endif]-->
</head>
<body>
	<div id="wrapper">
		<!-- logo -->
		<h1 class="logo"><a href="/"><%= properties.getProperty("site.title") %></a></h1>
		<!-- ad -->
		<jsp:include page="/inc/ads/banner.tpl" />
		<!-- main area -->
		<div id="main">
			<div id="twocolumns">
				<!-- content -->
				<div id="content">
					<div class="about">
						<h2><%= properties.getProperty("legal.termsofuse.linktext") %></h2>
						<p>The following Terms of Use outline your obligations when using the MMA Chronicles website. You can also review our Privacy Policy which outlines our obligations and practices towards handling any personal information that you may provide to us.</p>
						
						<strong>1. ACCEPTANCE OF TERMS</strong>
						<p>The web pages available at mmachronicles.com, and all linked pages ("Site"), are owned and operated by Charles Bihis ("Owner"), and is accessed by you under the Terms of Use described below ("Terms of Use").</p>
						<p>PLEASE READ THESE TERMS OF USE CAREFULLY BEFORE USING THE SERVICES. BY ACCESSING THIS SITE OR USING ANY PART OF THE SITE OR ANY CONTENT OR SERVICES (AS EACH IS DEFINED BELOW) ON THE SITE, YOU AGREE TO BECOME BOUND BY THESE TERMS AND CONDITIONS. IF YOU DO NOT AGREE TO ALL THE TERMS AND CONDITIONS, THEN YOU MAY NOT ACCESS THE SITE OR USE THE CONTENT OR ANY SERVICES IN THE SITE. OWNER'S ACCEPTANCE IS EXPRESSLY CONDITIONED UPON YOUR ASSENT TO ALL OF THESE TERMS AND CONDITIONS, TO THE EXCLUSION OF ALL OTHER TERMS; IF THESE TERMS AND CONDITIONS ARE CONSIDERED AN OFFER BY OWNER, ACCEPTANCE IS EXPRESSLY LIMITED TO THESE TERMS.</p>
						
						<strong>2. MODIFICATIONS OF TERMS OF USE</strong>
						<p>Owner reserves the right, at its sole discretion, to modify or replace the Terms of Use at any time. If the alterations constitute a material change to the Terms of Use, Owner will notify you by posting an announcement on the Site. What constitutes a "material change" will be determined at Owner's sole discretion, in good faith and using common sense and reasonable judgment. You shall be responsible for reviewing and becoming familiar with any such modifications. Use of the Services by you following such notification constitutes your acceptance of the terms and conditions of the Terms of Use as modified.</p>
						
						<strong>3. DESCRIPTION OF SERVICE</strong>
						<p>Subject to full compliance with the Terms of Use, Owner may offer to provide certain services and content, as described more fully on the Site, ("Services"). Services shall include, but not be limited to, any service and content Owner performs for you, as well as the offering of any materials displayed, transmitted or performed on the Site or through the Services (including, but not limited to text, user comments, messages, information, data, graphics, news articles, photographs, images, illustrations, software, audio clips and video clips, also known as the "Content"). Owner may change, suspend or discontinue the Services including any content for any reason, at any time, including the availability of any feature or content. Owner may also impose limits on certain features and services or restrict your access to parts or all of the Services without notice or liability.</p>
						
						<strong>4. COPYRIGHT COMPLAINTS</strong>
						<p>MMA Chronicles respects the intellectual property of others. It is MMA Chronicles's policy to respond expeditiously to claims of copyright and other intellectual property infringement. MMA Chronicles will promptly process and investigate notices of alleged infringement and will take appropriate actions under the Digital Millennium Copyright Act ("DMCA") and other applicable intellectual property laws. Upon receipt of notices complying or substantially complying with the DMCA, MMA Chronicles may act expeditiously to remove or disable access to any material claimed to be infringing or claimed to be the subject of infringing activity and may act expeditiously to remove or disable access to any reference or link to material or activity that is claimed to be infringing. MMA Chronicles will terminate access for subscribers and account holders who are repeat infringers.</p>
						<p>Notifying Owner of Copyright Infringement: To provide Owner notice of an infringement, you must provide a written communication to the attention of "DMCA Infringement Notification Dept." care of admin@mmachronicles.com that sets forth the information specified by the DMCA (http://www.copyright.gov/title17/92chap5.html#512). Please note that we may post your notification, with personally identifiable information redacted, to a clearinghouse such as chillingeffects.org. Please also note that you may be liable for damages (including costs and attorneys' fees) if you materially misrepresent that an activity is infringing your copyright.</p>
						<p>Providing Owner with Counter-Notification: If we remove or disable access to content in response to an infringement notice, we will make reasonable attempts to contact the owner or administrator of the affected site or content. If you feel that your material does not constitute infringement, you may provide Owner with a counter notification by written communication to the attention of "DMCA Counter Notification Dept." at admin@mmachronicles.com that sets forth all of the necessary information required by the DMCA (http://www.copyright.gov/title17/92chap5.html#512). Please note that you may be liable for damages (including costs and attorneys' fees) if you materially misrepresent that an activity is not infringing the copyrights of others. If you are uncertain whether an activity constitutes infringement, we recommended seeking advice of an attorney.</p>
						
						<strong>5. PRIVACY POLICY</strong>
						<p>MMA Chronicles's current privacy policy is available at mmachronicles.com/privacy-policy (the "Privacy Policy"), which is incorporated by this reference.</p>
						
						<strong>6. INDEMNITY</strong>
						<p>You will indemnify and hold harmless MMA Chronicles, its parents, subsidiaries, affiliates, customers, vendors, officers and employees from any liability, damage or cost (including reasonable attorneys. fees and cost) from (i) any claim or demand made by any third party due to or arising out of your access to the Site, use of the Services, violation of the Terms of Use by you, or the infringement by you, of any intellectual property or other right of any person or entity.</p>
						
						<strong>7. WARRANTY DISCLAIMERS</strong>
						<p>You acknowledge that MMA Chronicles has no control over, and no duty to take any action regarding: which users gain access to the Site or use the Services; what effects the content may have on you; how you may interpret or use the content; or what actions you may take as a result of having been exposed to the content. You release Owner from all liability for you having acquired or not acquired content through the Site or the Services. The Site or Services may contain, or direct you to sites containing, information that some people may find offensive or inappropriate. Owner makes no representations concerning any content contained in or accessed through the Site or Services, and Owner will not be responsible or liable for the accuracy, copyright compliance, legality or decency of material contained in or accessed through the Site or the Services. THE SERVICE, CONTENT, AND SITE ARE PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, WITHOUT LIMITATION, IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. SOME STATES DO NOT ALLOW LIMITATIONS ON HOW LONG AN IMPLIED WARRANTY LASTS, SO THE ABOVE LIMITATIONS MAY NOT APPLY TO YOU.</p>
						
						<strong>8. LINKS</strong>
						<p>The Services may provide, or third parties may provide, links to other World Wide Web sites or resources. Because MMA Chronicles has no control over such sites and resources, you acknowledge and agree that Owner is not responsible for the availability of such external sites or resources, and does not endorse and is not responsible or liable for any Content, advertising, products or other materials on or available from such sites or resources. You further acknowledge and agree that Owner shall not be responsible or liable, directly or indirectly, for any damage or loss caused or alleged to be caused by or in connection with use of or reliance on any such Content, goods or services available on or through any such site or resource.</p>
						
						<strong>9. LIMITATION OF LIABILITY</strong>
						<p>IN NO EVENT SHALL OWNER OR ITS SUPPLIERS BE LIABLE UNDER CONTRACT, TORT, STRICT LIABILITY, NEGLIGENCE OR OTHER LEGAL THEORY (I) WITH RESPECT TO THE SITE, THE SERVICE OR ANY CONTENT FOR ANY LOST PROFITS OR SPECIAL, INDIRECT, INCIDENTAL, PUNITIVE, OR CONSEQUENTIAL DAMAGES OF ANY KIND WHATSOEVER, SUBSTITUTE GOODS OR SERVICES (HOWEVER ARISING), OR (II) FOR ANY DIRECT DAMAGES IN EXCESS OF (IN THE AGGREGATE) $100. SOME STATES DO NOT ALLOW THE EXCLUSION OR LIMITATION OF INCIDENTAL OR CONSEQUENTIAL DAMAGES, SO THE ABOVE LIMITATIONS AND EXCLUSIONS MAY NOT APPLY TO YOU.</p>
						
						<strong>10. TERMINATION</strong>
						<p>Owner may terminate or suspend any and all Services immediately, without prior notice or liability, if you breach any of the terms or conditions of the Terms of Use. Upon termination of your account, your right to use the Services will immediately cease. All provisions of the Terms of Use which by their nature should survive termination shall survive termination, including, without limitation, ownership provisions, warranty disclaimers, indemnity and limitations of liability.</p>
						
						<strong>11. MISCELLANEOUS</strong>
						<p>No agency, partnership, joint venture, or employment is created as a result of the Terms of Use and you do not have any authority of any kind to bind Owner in any respect whatsoever. The failure of either party to exercise in any respect any right provided for herein shall not be deemed a waiver of any further rights hereunder. Owner shall not be liable for any failure to perform its obligations hereunder where such failure results from any cause beyond Owner's reasonable control, including, without limitation, mechanical, electronic or communications failure or degradation (including "line-noise" interference). If any provision of the Terms of Use is found to be unenforceable or invalid, that provision shall be limited or eliminated to the minimum extent necessary so that the Terms of Use shall otherwise remain in full force and effect and enforceable. The Terms of Use is not assignable, transferable or sublicensable by you except with Owner's prior written consent. Owner may transfer, assign or delegate the Terms of Use and its rights and obligations without consent.</p>
						
						<strong>12. TRADEMARKS</strong>
						<p>MMA CHRONICLES and MMA Chronicles graphics, logos, designs, page headers, button icons, scripts, and service names are registered trademarks, trademarks or trade dress of MMA Chronicles. MMA Chronicles's trademarks and trade dress may not be used, including as part of trademarks and/or as part of domain names, in connection with any product or service in any manner that is likely to cause confusion.</p>
					</div>
				</div>
				<jsp:include page="/inc/sidebar-legal.jsp" />
			</div>
			<div class="main-b">&nbsp;</div>
		</div>
		<jsp:include page="/inc/header.tpl" />
		<jsp:include page="/inc/footer.jsp" />
	</div>
</body>
</html>
