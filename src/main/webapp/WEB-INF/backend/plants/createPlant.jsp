<%@include file="/WEB-INF/includes/header.jsp" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Page Heading
            <small>Secondary Text</small>
        </h1>
    </div>
</div>

<div class="row">
    <form class="form-horizontal">
        <div class="form-group">
            <label class='control-label col-sm-2' for="plantName">Name</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" id="plantName">
            </div>
        </div>
        <div class="form-group">
            <label class='control-label col-sm-2' for="plantDescr">Description</label>
            <div class="col-sm-7">
                <textarea class="form-control" rows="5" id="plantDescr"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class='control-label col-sm-2' for="plantImg">Description</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" id="plantImg">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>

    </form>
</div>

<hr>
<%@include file="/WEB-INF/includes/footer.jsp" %>