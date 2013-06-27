package qrcode

class QrcodeTagLib {
    static namespace = "qrcode"
    def QRCodeService
    
    /**
     * Create qrcode follow google api format
     * Please see: https://developers.google.com/chart/infographics/docs/qr_codes
     * @author Tung Ly
     */
    def google = { attrs ->
        if(attrs.information){
            def base64Image = QRCodeService.generateQRCodeBase64(attrs.information,attrs?.logoLink)
            if(base64Image){
                out << "<img src='data:image/png;base64," + base64Image + "' />"
            }else{
                out << "CONNECTION ERROR"
            }
        }else{
            println "qrcode tag: \"information\" attribute is not existed"
        }
    }

    /**
     * Create qrcode for MECARD
     * contactInfos: [FIRSTNAME, LASTNAME, ADR, TEL, TEL-AV, EMAIL, SOUND, NOTE, URL, BDAY, NICKNAME]
     * Please see: http://www.nttdocomo.co.jp/english/service/developer/make/content/barcode/function/application/addressbook/index.html
     * @author Tung Ly
     */
    def contact = { attrs ->
        if(attrs.contactInfos){
            def base64Image = QRCodeService.generateQRCodeBase64(QRCodeService.convertUserInfosToCHL(attrs.contactInfos),attrs?.logoLink)
            if(base64Image){
                out << "<img src='data:image/png;base64," + base64Image + "' />"
            }else{
                out << "CONNECTION ERROR"
            }
        }else{
            println "qrcode tag: \"contactInfos\" attribute is not existed"
        }
    }


    /**
     * Create qrcode for a text.
     * @author Tung Ly
     */
    def text = { attrs, body ->
        if(body()){
            Map<String, String> information = [chl: "chl=" + URLEncoder.encode(body().trim())];
//            Map<String, String> information = [chl: "chl=sdfsdfsdfsdf"];
            def base64Image = QRCodeService.generateQRCodeBase64(information, ,attrs?.logoLink)
            if(base64Image){
                out << "<img src='data:image/png;base64," + base64Image + "' />"
            }else{
                out << "CONNECTION ERROR"
            }
        }else{
            println "qrcode tag: body is null"
        }
    }
}
