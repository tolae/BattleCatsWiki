(function(L) {
    function B() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($("#cost_main").text());
                $("#cost_main").html(a);
                $("input", "#cost_main").focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    t(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", "#cost_main").keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), t(this), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function C() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    u(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), u(this), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function D() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    v(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), v(this), $(this).after($(this).val()).unbind().remove(),
                        $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function E() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    w(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), w(this), $(this).after($(this).val()).unbind().remove(),
                        $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function F() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    x(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), x(this), $(this).after($(this).val()).unbind().remove(),
                        $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function G() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("font.c05").index(this),
                    c = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(c);
                $("input", this).focus().blur(function() {
                    var c = d(this);
                    $(this).val(c);
                    g(this, a);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(c) {
                    13 === c.which && (c = d(this),
                        $(this).val(c), g(this, a), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function H() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("font.c06").index(this),
                    c = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(c);
                $("input", this).focus().blur(function() {
                    var c = d(this);
                    $(this).val(c);
                    h(this, a);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(c) {
                    13 === c.which && (c = d(this), $(this).val(c), h(this, a), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function I() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    y(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), y(this), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function J() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    z(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), z(this), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function K() {
        var b = !1;
        return function() {
            if (!b) {
                var a = $("<input class='custom1'>").attr("type", "text").val($(this).text());
                $(this).html(a);
                $("input", this).focus().blur(function() {
                    var a = d(this);
                    $(this).val(a);
                    A(this);
                    $(this).after($(this).val()).unbind().remove();
                    $("#List").trigger("update");
                    $("#List").trigger("appendCache");
                    b = !1
                });
                $("input", this).keypress(function(a) {
                    13 === a.which && (a = d(this), $(this).val(a), A(this), $(this).after($(this).val()).unbind().remove(), $("#List").trigger("update"), $("#List").trigger("appendCache"), b = !1)
                });
                b = !0
            }
        }
    }

    function d(b) {
        return "" == $(b).val() ? b = 1 : $(b).val().replace(/[\uff21-\uff3a\uff41-\uff5a\uff10-\uff19\uff0e]/g, function(a) {
            return String.fromCharCode(a.charCodeAt(0) - 65248)
        })
    }

    function w(b) {
        for (var a = 0; a < $("font.c05").length; a++) $("font.c05").eq(a).text($(b).val()),
            g(b, a)
    }

    function x(b) {
        for (var a = 0; a < $("font.c06").length; a++) $("font.c06").eq(a).text($(b).val()), h(b, a)
    }

    function g(b, a) {
        var c = parseFloat($(b).val()),
            d = parseFloat($("font.c06").eq(a).text());
        c = f(a, c, d);
        tdc08 = "-" == $("font.c07").eq(a).text() ? "-" : Math.floor(Math.round($("font.c07").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraH").text() / 300)));
        at1c = Math.floor(Math.round($("font.at1").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraA").text() / 300)));
        at2c = Math.floor(Math.round($("font.at2").eq(a).text() * ((c + 4) /
            5)) * (1 + 1.5 * ($("#takaraA").text() / 300)));
        at3c = Math.floor(Math.round($("font.at3").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraA").text() / 300)));
        tdc12 = at1c + at2c + at3c;
        tdc13 = "-" == $("font.c13").eq(a).text() ? "-" : Math.round(tdc12 / $("font.c15").eq(a).text() * 3E3 / 100);
        k(a, tdc08);
        l(a, at1c);
        m(a, at2c);
        n(a, at3c);
        p(a, tdc12);
        q(a, tdc13)
    }

    function h(b, a) {
        var c = parseFloat($("font.c05").eq(a).text()),
            d = parseFloat($(b).val());
        c = f(a, c, d);
        tdc08 = "-" == $("font.c07").eq(a).text() ? "-" : Math.floor(Math.round($("font.c07").eq(a).text() *
            ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraH").text() / 300)));
        at1c = Math.floor(Math.round($("font.at1").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraA").text() / 300)));
        at2c = Math.floor(Math.round($("font.at2").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraA").text() / 300)));
        at3c = Math.floor(Math.round($("font.at3").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * ($("#takaraA").text() / 300)));
        tdc12 = at1c + at2c + at3c;
        tdc13 = "-" == $("font.c13").eq(a).text() ? "-" : Math.round(tdc12 / $("font.c15").eq(a).text() * 3E3 / 100);
        k(a, tdc08);
        l(a, at1c);
        m(a, at2c);
        n(a,
            at3c);
        p(a, tdc12);
        q(a, tdc13)
    }

    function y(b) {
        for (var a = 0; a < $("font.c07").length; a++) {
            var c = parseFloat($("font.c05").eq(a).text()),
                d = parseFloat($("font.c06").eq(a).text());
            c = f(a, c, d);
            tdc08 = "-" == $("font.c07").eq(a).text() ? "-" : Math.floor(Math.round($("font.c07").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * (parseFloat($(b).val()) / 300)));
            k(a, tdc08)
        }
    }

    function z(b) {
        for (var a = 0; a < $("font.c11").length; a++) {
            var c = parseFloat($("font.c05").eq(a).text()),
                d = parseFloat($("font.c06").eq(a).text());
            c = f(a, c, d);
            at1c = Math.floor(Math.round($("font.at1").eq(a).text() *
                ((c + 4) / 5)) * (1 + 1.5 * (parseFloat($(b).val()) / 300)));
            at2c = Math.floor(Math.round($("font.at2").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * (parseFloat($(b).val()) / 300)));
            at3c = Math.floor(Math.round($("font.at3").eq(a).text() * ((c + 4) / 5)) * (1 + 1.5 * (parseFloat($(b).val()) / 300)));
            tdc12 = at1c + at2c + at3c;
            tdc13 = "-" == $("font.c13").eq(a).text() ? "-" : Math.round(tdc12 / $("font.c15").eq(a).text() * 3E3 / 100);
            l(a, at1c);
            m(a, at2c);
            n(a, at3c);
            p(a, tdc12);
            q(a, tdc13)
        }
    }

    function f(b, a, c) {
        b = $(".c02").eq(b).text();
        a += c;
        "1\u57fa\u672c" == b ? 60 < a && (a = (a -
            60) / 2 + 60) : "2EX\u30c9\u30ed\u30c3\u30d7" == b ? 30 < a && (a = (a - 30) / 2 + 30) : "2EX" == b ? 60 < a && (a = (a - 60) / 2 + 60) : "3\u30ec\u30a2" == b ? 90 < a ? a = 80 + (a - 90) / 4 : 70 < a && (a = (a - 70) / 2 + 70) : "4\u6fc0\u30ec\u30a2" == b ? 80 < a ? a = 70 + (a - 80) / 4 : 60 < a && (a = (a - 60) / 2 + 60) : "4\u6fc0\u30ec\u30a2\u30e1\u30bf\u30eb" != b && ("4\u6fc0\u30ec\u30a2\u72c2\u4e71" == b ? 20 < a && (a = 20 + (a - 20) / 2) : "5\u8d85\u6fc0\u30ec\u30a2" == b && (80 < a ? a = 70 + (a - 80) / 4 : 60 < a && (a = (a - 60) / 2 + 60)));
        return a
    }

    function A(b) {
        for (var a = 0; a < $("font.c19").length; a++) {
            var c = $("font.c19").eq(a).text() - (.3 *
                parseFloat($(b).val()) + 6 * (parseFloat($("#kenkyuLv").text()) + parseFloat($("#kenkyuPlus").text()) - 1));
            60 > c && (c = 60);
            c = Math.floor(c);
            r(a, c)
        }
    }

    function u(b) {
        for (var a = 0; a < $("font.c19").length; a++) {
            var c = $("font.c19").eq(a).text() - (.3 * $("#takaraS").text() + 6 * (parseFloat($(b).val()) + parseFloat($("#kenkyuPlus").text()) - 1));
            60 > c && (c = 60);
            c = Math.floor(c);
            r(a, c)
        }
    }

    function v(b) {
        for (var a = 0; a < $("font.c19").length; a++) {
            var c = $("font.c19").eq(a).text() - (.3 * $("#takaraS").text() + 6 * (parseFloat($("#kenkyuLv").text()) +
                parseFloat($(b).val()) - 1));
            60 > c && (c = 60);
            c = Math.floor(c);
            r(a, c)
        }
    }

    function t(b) {
        for (var a = 0; a < $("font.c17").length; a++) {
            var c = 1 == $(b).val() ? $("font.c17").eq(a).text() : 2 == $(b).val() ? 1.5 * $("font.c17").eq(a).text() : 3 == $(b).val() ? 2 * $("font.c17").eq(a).text() : $("font.c17").eq(a).text();
            c = Math.floor(c);
            var d = a,
                e = Number(c).toLocaleString();
            100 >= c ? $("font.c18").eq(d).removeAttr("class").addClass("c18 H").text(e) : 200 >= c ? $("font.c18").eq(d).removeAttr("class").addClass("c18 LH").text(e) : 4E3 <= c ? $("font.c18").eq(d).removeAttr("class").addClass("c18 L").text(e) :
                2E3 <= c ? $("font.c18").eq(d).removeAttr("class").addClass("c18 LL").text(e) : $("font.c18").eq(d).removeAttr("class").addClass("c18").text(e)
        }
    }

    function k(b, a) {
        if ("-" == a) $("font.c08").eq(b);
        else {
            var c = Number(a).toLocaleString();
            1E5 <= a ? $("font.c08").eq(b).removeAttr("class").addClass("c08 H").text(c) : 5E4 <= a ? $("font.c08").eq(b).removeAttr("class").addClass("c08 LH").text(c) : 2E3 >= a ? $("font.c08").eq(b).removeAttr("class").addClass("c08 L").text(c) : 1E4 >= a ? $("font.c08").eq(b).removeAttr("class").addClass("c08 LL").text(c) :
                $("font.c08").eq(b).removeAttr("class").addClass("c08").text(c)
        }
    }

    function l(b, a) {
        var c = Number(a).toLocaleString();
        $("font.at1c").eq(b).text(c)
    }

    function m(b, a) {
        var c = Number(a).toLocaleString();
        $("font.at2c").eq(b).text(c)
    }

    function n(b, a) {
        var c = Number(a).toLocaleString();
        $("font.at3c").eq(b).text(c)
    }

    function p(b, a) {
        var c = Number(a).toLocaleString();
        5E4 <= a ? $("font.c12").eq(b).removeAttr("class").addClass("c12 H").text(c) : 25E3 <= a ? $("font.c12").eq(b).removeAttr("class").addClass("c12 LH").text(c) : 1E3 >=
            a ? $("font.c12").eq(b).removeAttr("class").addClass("c12 L").text(c) : 5E3 >= a ? $("font.c12").eq(b).removeAttr("class").addClass("c12 LL").text(c) : $("font.c12").eq(b).removeAttr("class").addClass("c12").text(c)
    }

    function q(b, a) {
        if ("-" == a) $("font.c13").eq(b);
        else {
            var c = Number(a).toLocaleString();
            1E4 <= a ? $("font.c13").eq(b).removeAttr("class").addClass("c13 H").text(c) : 5E3 <= a ? $("font.c13").eq(b).removeAttr("class").addClass("c13 LH").text(c) : 150 >= a ? $("font.c13").eq(b).removeAttr("class").addClass("c13 L").text(c) :
                500 >= a ? $("font.c13").eq(b).removeAttr("class").addClass("c13 LL").text(c) : $("font.c13").eq(b).removeAttr("class").addClass("c13").text(c)
        }
    }

    function r(b, a) {
        90 >= a ? ($("font.c20").eq(b).removeAttr("class").addClass("c20 H").text(a), $("font.c30").eq(b).removeAttr("class").addClass("c30 H").text((a / 30).toFixed(2) + "\u79d2")) : 150 >= a ? ($("font.c20").eq(b).removeAttr("class").addClass("c20 LH").text(a), $("font.c30").eq(b).removeAttr("class").addClass("c30 LH").text((a / 30).toFixed(2) + "\u79d2")) : 3E3 <= a ? ($("font.c20").eq(b).removeAttr("class").addClass("c20 L").text(a),
            $("font.c30").eq(b).removeAttr("class").addClass("c30 L").text((a / 30).toFixed(2) + "\u79d2")) : 1500 <= a ? ($("font.c20").eq(b).removeAttr("class").addClass("c20 LL").text(a), $("font.c30").eq(b).removeAttr("class").addClass("c30 LL").text((a / 30).toFixed(2) + "\u79d2")) : ($("font.c20").eq(b).removeAttr("class").addClass("c20").text(a), $("font.c30").eq(b).removeAttr("class").addClass("c30").text((a / 30).toFixed(2) + "\u79d2"))
    }
    $(document).ready(function() {
        $("#allLv").click(E());
        $("#allPlus").click(F());
        $("font.c05").click(G());
        $("font.c06").click(H());
        $("#takaraH").click(I());
        $("#takaraA").click(J());
        $("#takaraS").click(K());
        $("#kenkyuLv").click(C());
        $("#kenkyuPlus").click(D());
        $("#cost").click(B())
    })
})(document);