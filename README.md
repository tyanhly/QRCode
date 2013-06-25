QRCode
======

Grail plugin project - Create QRCode and can put your logo to QRCode image

This plugin create qrcode image by google API. And it can combine your logo to this image.

== Installation

== Using

= Information
<pre>

HashMap information = new HashMap<String, String>();
        def contactInfo = "MECARD:N:Tung,Ly;ADR:76 9th Avenue, 4th Floor, New York, NY 10011;"\
                        + "TEL:+84906667100;EMAIL:tyanhly@example.com;;"
        information.put("chs", "chs=250x250")
        information.put("cht", "cht=qr")
        information.put("chl", "chl=" + URLEncoder.encode(contactInfo))
        information.put("chld", "chld=H|1")
        information.put("choe", "choe=UTF-8")
        QRCodeService.createQRCode(information, "/local/logo.jpg", "/local", "qrcode.png")
</pre>

or

<pre>
def bytes = new URL("http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png").getBytes()
        def data = bytes.encodeBase64().toString();
        QRCodeService.createQRCodeAndLogoBase64(information, data, "/local", "google.png" )
</pre>

= Contact information
<pre>
def contactInfos = new HashMap<String, String>();
        contactInfos.put("FIRSTNAME", "Tung");
        contactInfos.put("LASTNAME", "Ly");
        contactInfos.put("ADR", "124 Cao Xuan Duc P12 Q8 HCM");
        contactInfos.put("TEL", "0906667100");
        contactInfos.put("TEL-AV", "+84906667100");
        contactInfos.put("EMAIL", "tyanhly@yahoo.com");
        contactInfos.put("URL", "http://kiss-concept.com");
        contactInfos.put("BDAY", "1985/01/01");
        
        QRCodeService.createContactQRCode(contactInfos, "http://kiss-concept.com/images/logo_kwwiss_concept.png", \
            "/local", "qrcode2.png")
</pre>

or

<pre>
def abytes = new URL("http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png").getBytes()
        def adata = abytes.encodeBase64().toString();
        
        QRCodeService.createContactQRCodeAndLogoBase64(contactInfos, dataa, "/local", "ibm.png" )
</pre>

