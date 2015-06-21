/**
 * Created by ִלטענטי on 21.06.2015.
 */
function getTs() {

    var ts = new Date();

    var h = ts.getHours();
    if (h < 10) h = '0' + h;

    var m = ts.getMinutes();
    if (m < 10) m = '0' + m;

    var s = ts.getSeconds();
    if (s < 10) s = '0' + s;

    var ms = ts.getMilliseconds();
    if (ms < 10) {
        ms = '00' + ms;
    } else if (ms < 100) {
        ms = '0' + ms;
    }

    return h + ":" + m + ":" + s + "." + ms;
}