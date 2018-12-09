<!doctype html><html lang="hu">
<%@ page pageEncoding="UTF-8" %>
<head>
    <%@include file="../includes/head.jsp" %>
    <link href="/common.css" rel="stylesheet">
    <script type="text/javascript" src="/Common.js"></script>
    <script type="text/javascript" src="/ImportPage.js?v=1"></script>
</head>

<body>
    <div class="container">
        <div class="shadow-sm p-3 mb-5 bg-white rounded">
            <%@include file="../includes/nav.jsp" %>
            <div class="container">
                <h2>Import</h2>

                <form method="POST" action="/processImport" enctype="multipart/form-data">
                    <div class="input-group mb-3 form-group">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="type">Típus</label>
                        </div>
                        <select class="custom-select" id="type" name="type">
                            <option selected>Kérlek, válassz...</option>
                            <option value="1">Erste</option>
                            <option value="2">Magnet</option>
                        </select>
                        <div class="invalid-feedback">
                            <div id="type-required" class="field-error">Kötelező kiválasztani</div>
                            <div id="type-invalid" class="field-error">Csak Erste és Magnet választható</div>
                        </div>
                    </div>

                    <div class="input-group form-group">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="file" name="file" aria-describedby="inputGroupFileAddon04">
                            <label class="custom-file-label" for="file">Válaszd ki az importálandó file-t...</label>
                        </div>
                        <div class="invalid-feedback">
                            <div id="file-invalidext" class="field-error">Csak .pdf és .xml file-ok tölthetőek fel</div>
                            <div id="file-invalidpdf" class="field-error">Hibás PDF file</div>
                        </div>
                    </div>
                    <div class="input-group form-group">
                        <button type="submit" class="btn btn-success">Success</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>

</html>