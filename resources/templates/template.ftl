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
            <#include 'include/${pageName}.html'/>
        </div>
        <#--<#include 'include/footer.html'/>-->
    </body>
</html>