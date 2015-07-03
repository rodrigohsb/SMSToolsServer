<!DOCTYPE html>
<html>
    <head>
        <title>AMX Callcenter</title>
        <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
        <script type="text/javascript" src="${rc.getContextPath()}/estatico/js/jquery.js"></script>
        <script type="text/javascript" src="${rc.getContextPath()}/estatico/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${rc.getContextPath()}/estatico/js/TableTools.js"></script>

        <link href="${rc.getContextPath()}/estatico/style/style.css" rel="stylesheet" type="text/css"/>
        <link href="${rc.getContextPath()}/estatico/style/reset.css" rel="stylesheet" type="text/css"/>
        <link href="${rc.getContextPath()}/estatico/style/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" />
        <link href="${rc.getContextPath()}/estatico/style/demo_table_jui.css" rel="stylesheet" type="text/css" />
        <link href="${rc.getContextPath()}/estatico/style/jquery.dataTables.css" rel="stylesheet" type="text/css" />
        <link href="${rc.getContextPath()}/estatico/style/modal.css" rel="stylesheet" type="text/css" />
        <link href="${rc.getContextPath()}/estatico/style/loading.css" rel="stylesheet" type="text/css" />

    </head>
    <body>
        <#include 'include/header.html'/>
            <div id="container">
                <div class="error">
                    <div>Por favor, escolha seu país abaixo:</div>
                    <br/>
                    <div class="aaa">
                        <div class="flag ar"> </div><a href="${rc.getContextPath()}/ar">&nbsp;Argentina&nbsp;&nbsp;|&nbsp;&nbsp;</a>
                        <div class="flag ec"> </div><a href="${rc.getContextPath()}/ec">&nbsp;Ecuador&nbsp;&nbsp;|&nbsp;&nbsp;</a>
                        <div class="flag mx"> </div><a href="${rc.getContextPath()}/mx">&nbsp;Mexico&nbsp;&nbsp;|&nbsp;&nbsp;</a>
                        <div class="flag pe"> </div><a href="${rc.getContextPath()}/pe">&nbsp;Peru&nbsp;&nbsp;|&nbsp;&nbsp;</a>
                        <div class="flag pr"> </div><a href="${rc.getContextPath()}/pr">&nbsp;Puerto Rico</a>
                    </div>
                </div>
        </div>
        <#--<#include 'include/footer.html'/>-->
    </body>
</html>