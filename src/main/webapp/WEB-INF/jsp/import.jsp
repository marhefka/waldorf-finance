<!doctype html><html lang="hu">
<%@ page pageEncoding="UTF-8" %>
<head>
    <%@include file="../includes/head.jsp" %>
</head>

<body>
    <div class="container">
        <div class="shadow-sm p-3 mb-5 bg-white rounded">
            <%@include file="../includes/nav.jsp" %>
            <div class="container">
                <h2>Import</h2>

                <form method="POST" action="/upload" enctype="multipart/form-data">
                    <div class="input-group mb-3 form-group">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">Típus</label>
                        </div>
                        <select class="custom-select" id="inputGroupSelect01">
                            <option selected>Kérlek, válassz...</option>
                            <option value="1">Erste</option>
                            <option value="2">Magnet</option>
                        </select>
                    </div>

                    <div class="input-group form-group">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="file" name="file" aria-describedby="inputGroupFileAddon04">
                            <label class="custom-file-label" for="file">Válaszd ki az importálandó file-t...</label>
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