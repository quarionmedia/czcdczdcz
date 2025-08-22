<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!doctype html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <title>Dashboard | Dooo - Movie & Web Series Portal App</title>

    <?php include("partials/header.php"); ?>

</head>

<body data-sidebar="dark">

<!-- Begin page -->
<div id="layout-wrapper">


    <?php include("partials/topbar.php"); ?>


    <?php include("partials/sidebar.php"); ?>


    <!-- ============================================================== -->
    <!-- Start right Content here -->
    <!-- ============================================================== -->
    <div class="main-content">

        <div class="page-content">

            <div class="container-fluid">



                <!-- start page title -->

                <div class="row align-items-center">

                    <div class="col-sm-6">

                        <div class="page-title-box">

                            <h4 class="font-size-18">Subtitle Manager</h4>

                            <ol class="breadcrumb mb-0">

                                <li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

                                <li class="breadcrumb-item"><a href="javascript: void(0);">Contents</a></li>

                                <li class="breadcrumb-item active">Subtitle Manager</li>

                            </ol>

                        </div>

                    </div>

                </div>

                <!-- end page title -->


                <div class="row">
                    <div class="col-md-12">
                        <div class="card card-body">
                            <div
                                class="d-flex justify-content-between align-items-center flex-wrap grid-margin">
                                <div>
                                    <h4 class="mb-3 mb-md-0">Subtitle Manager <?php if($ct == 1) {echo "($LinkDetails->name)";} else if($ct == 2) {echo "($LinkDetails->name)";}  ?></h4>
                                </div>
                                <div class="d-flex align-items-center flex-wrap text-nowrap">
                                    <div class="panel-heading">
                                        <button data-bs-toggle="modal" data-bs-target="#Add_Subtitle_Modal"
                                                id="Add_Subtitle"
                                                class="btn btn-sm btn-primary waves-effect waves-light"><span
                                                class="btn-label"><i class="fa fa-plus"></i></span> Add
                                            Subtitle</button>
                                    </div>
                                </div>
                            </div>

                            <br>

                            <div>

                                <table id="datatable" class="table table-striped"
                                       style="border-collapse: collapse; border-spacing: 0; width: 100%;">

                                    <thead>

                                    <tr>

                                        <th>#</th>

                                        <th>Language</th>

                                        <th>Subtitle Url</th>

                                        <th>Mime Type</th>

                                        <th>Status</th>

                                        <th>Options</th>

                                    </tr>

                                    </thead>


                                    <tbody>
                                    <?php
                                    $int_table_id = 1;
                                    foreach ($subtitles as $row) {
                                        ?>
                                        <tr>
                                            <th><?php echo($int_table_id); ?></th>
                                            <td><?php echo stripslashes($row->language); ?></td>
                                            <td><?php echo wordwrap($row->subtitle_url,60,"<br>\n",TRUE); ?>
                                            </td>
                                            <td><?php echo stripslashes($row->mime_type); ?></td>

                                            <?php
                                            if($row->status == 0) {
                                                ?>
                                                <td><span class="badge bg-danger">UnPublished</span></td>
                                                <?php
                                            }
                                            if($row->status == 1) {
                                                ?>
                                                <td><span class="badge bg-success">Published</span></td>
                                                <?php
                                            }

                                            ?>

                                            <td>

                                                <div class="btn-group mr-1 mt-2">
                                                    <?php $Row_ID = $row->id; ?>

                                                    <button type="button"
                                                            class="btn btn-primary btn-sm dropdown-toggle"
                                                            data-bs-toggle="dropdown" aria-haspopup="true"
                                                            aria-expanded="false">Edit</button>

                                                    <div class="dropdown-menu" style="">

                                                        <a class="dropdown-item"
                                                           onclick="Load_Subtitle_Data(<?php echo($Row_ID); ?>)"
                                                           data-bs-toggle="modal"
                                                           data-bs-target="#Edit_Subtitle_Modal">Edit
                                                            Subtitle</a>
                                                        <a class="dropdown-item" id="Delete"
                                                           onclick="Delete_Subtitle(<?php echo($Row_ID); ?>)">Delete</a>

                                                    </div>

                                                </div>

                                            </td>



                                            <?php ++$int_table_id ?>
                                        </tr>
                                        <?php
                                    }
                                    ?>
                                    </tbody>

                                </table>

                            </div>

                        </div>
                    </div>
                </div>


            </div> <!-- container-fluid -->

        </div>

        <!-- Add Modal -->
        <div class="modal fade" id="Add_Subtitle_Modal" tabindex="-1" role="dialog"
             aria-labelledby="Movie_Link_Edit_Modal_Lebel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="Movie_Link_Edit_Modal_Lebel">Add Subtitle</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="panel-body">
                            <div class="form-group"> <label
                                    class="control-label">Language</label>&nbsp;&nbsp;<input
                                    id="modal_add_Language" type="text" name="label" class="form-control"
                                    placeholder="" required=""> </div>

                            <div class="form-group"> <label class="control-label">Subtitle
                                    Url</label>&nbsp;&nbsp;<input id="modal_add_Subtitle_url" type="text"
                                                                  name="label" class="form-control" placeholder="" required=""> </div>


                            <div class="form-group"> <label class="control-label">Mime Type</label> <select
                                    id="modal_add_Mimetype" class="form-control" name="Mimetype"
                                    id="selected-source">
                                    <option value="WebVTT">WebVTT</option>
                                    <option value="TTML">TTML</option>
                                    <option value="SMPTE-TT">SMPTE-TT</option>
                                    <option value="SubRip">SubRip</option>
                                    <option value="SubStationAlpha-SSA">SubStationAlpha-SSA</option>
                                    <option value="SubStationAlpha-ASS">SubStationAlpha-ASS</option>
                                </select> </div>

                            <div class="form-group"> <label class="control-label">Status</label> <select
                                    id="modal_Add_Status" class="form-control" name="source">
                                    <option value="Publish" selected="">Publish</option>
                                    <option value="Unpublish">Unpublish</option>
                                </select> </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" onclick="Add_Subtitle()" class="btn btn-primary">Add
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Modal -->
        <div class="modal fade" id="Edit_Subtitle_Modal" tabindex="-1" role="dialog"
             aria-labelledby="Movie_Link_Edit_Modal_Lebel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="Movie_Link_Edit_Modal_Lebel">Edit Subtitle</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="panel-body">
                            <input type="hidden" id="edit_subtitle_id" name="edit_subtitle_id" value="000">

                            <div class="form-group"> <label
                                    class="control-label">Language</label>&nbsp;&nbsp;<input
                                    id="modal_edit_Language" type="text" name="label" class="form-control"
                                    placeholder="" required=""> </div>

                            <div class="form-group"> <label class="control-label">Subtitle
                                    Url</label>&nbsp;&nbsp;<input id="edit_subtitle_url" type="text"
                                                                  name="label" class="form-control" placeholder="" required=""> </div>


                            <div class="form-group"> <label class="control-label">Mime Type</label> <select
                                    id="modal_edit_mimetype" class="form-control" name="Language"
                                    id="selected-source">
                                    <option value="WebVTT">WebVTT</option>
                                    <option value="TTML">TTML</option>
                                    <option value="SMPTE-TT">SMPTE-TT</option>
                                    <option value="SubRip">SubRip</option>
                                    <option value="SubStationAlpha-SSA">SubStationAlpha-SSA</option>
                                    <option value="SubStationAlpha-ASS">SubStationAlpha-ASS</option>
                                </select> </div>

                            <div class="form-group"> <label class="control-label">Status</label> <select
                                    id="modal_edit_Status" class="form-control" name="source">
                                    <option value="Publish" selected="">Publish</option>
                                    <option value="Unpublish">Unpublish</option>
                                </select> </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" onclick="Update_Subtitle_Data()" class="btn btn-primary">Update
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <?php include("partials/footer_rights.php"); ?>


    </div>
    <!-- end main content-->

</div>
<!-- END layout-wrapper -->

<?php include("partials/footer.php"); ?>

<script>
    $('#datatable').dataTable({
        "order": [],
        "ordering": false,
        "paging": false,
        "info": false,
        "filter": false,
        "pageLength": 100
    });

    function Add_Subtitle() {
        var modal_add_Language = document.getElementById("modal_add_Language").value;
        var modal_add_Subtitle_url = document.getElementById("modal_add_Subtitle_url").value;
        var modal_add_Mimetype = document.getElementById("modal_add_Mimetype").value;

        var modal_Add_Status = document.getElementById("modal_Add_Status").value;
        if (modal_Add_Status == "Publish") {
            var Status_int = "1";
        } else if (modal_Add_Status == "Unpublish") {
            var Status_int = "0";
        }

        var jsonObjects = {
            content_id: "<?php echo $ID; ?>",
            content_type: "<?php echo $ct; ?>",
            modal_add_Language: modal_add_Language,
            modal_add_Subtitle_url: modal_add_Subtitle_url,
            modal_add_Mimetype: modal_add_Mimetype,
            Status_int: Status_int
        };
        $.ajax({
            type: 'POST',
            url: '<?= site_url('Admin_api/add_subtitle') ?>',
            data: jsonObjects,
            dataType: 'text',
            success: function (response2) {
                if (response2 != "") {
                    swal.fire({
                        title: 'Successful!',
                        text: 'Subtitle Added Successfully!',
                        icon: 'success',
                        showCancelButton: false,
                        confirmButtonColor: '#556ee6',
                        cancelButtonColor: "#f46a6a"
                    }).then(function () {
                        location.reload();
                    });
                } else {
                    swal.fire({
                        title: 'Error',
                        text: 'Something Went Wrong :(',
                        icon: 'error'
                    }).then(function () {
                        location.reload();
                    });
                }
            }
        });
    }

    function Load_Subtitle_Data(ID) {
        var jsonObjects = {
            subtitleID: ID
        };
        $.ajax({
            type: 'POST',
            url: '<?= site_url('Admin_api/get_subtitle_details') ?>',
            data: jsonObjects,
            dataType: 'json',
            success: function (response1) {
                var id = response1.id;
                var language = response1.language;
                var subtitle_url = response1.subtitle_url;
                var mime_type = response1.mime_type;
                var status = response1.status;

                if (!id == "") {
                    $("#edit_subtitle_id").val(id);
                    $("#modal_edit_Language").val(language);
                    $("#edit_subtitle_url").val(subtitle_url);
                    $("#modal_edit_mimetype").val(mime_type);
                    if (status == "1") {
                        $("#modal_edit_Status").val("Publish");
                    } else if (status == "0") {
                        $("#modal_edit_Status").val("Unpublish");
                    }
                }
            }
        });
    }

    function Update_Subtitle_Data() {
        var edit_subtitle_id = document.getElementById("edit_subtitle_id").value;
        var modal_edit_Language = document.getElementById("modal_edit_Language").value;
        var edit_subtitle_url = document.getElementById("edit_subtitle_url").value;
        var modal_edit_mimetype = document.getElementById("modal_edit_mimetype").value;

        var Status_Txt = document.getElementById("modal_edit_Status").value;
        if (Status_Txt == "Publish") {
            var Status = "1";
        } else if (Status_Txt == "Unpublish") {
            var Status = "0";
        }

        var jsonObjects = {
            edit_subtitle_id: edit_subtitle_id,
            modal_edit_Language: modal_edit_Language,
            edit_subtitle_url: edit_subtitle_url,
            modal_edit_mimetype: modal_edit_mimetype,
            Status: Status

        };
        $.ajax({
            type: 'POST',
            url: '<?= site_url('Admin_api/update_subtitle') ?>',
            data: jsonObjects,
            dataType: 'text',
            success: function (response5) {
                if (response5) {
                    swal.fire({
                        title: 'Successful!',
                        text: 'Subtitle Updated Successfully!',
                        icon: 'success',
                        showCancelButton: false,
                        confirmButtonColor: '#556ee6',
                        cancelButtonColor: "#f46a6a"
                    }).then(function () {
                        location.reload();
                    });
                } else {
                    swal.fire({
                        title: 'Error',
                        text: 'Something Went Wrong :(',
                        icon: 'error'
                    }).then(function () {
                        location.reload();
                    });
                }
            }
        });
    }

    function Delete_Subtitle(ID) {
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#34c38f",
            cancelButtonColor: "#f46a6a",
            confirmButtonText: "Yes, delete it!"
        }).then(function (result) {
            if (result.value) {
                var jsonObjects = {
                    subtitleID: ID
                };
                $.ajax({
                    type: 'POST',
                    url: '<?= site_url('Admin_api/delete_subtitle') ?>',
                    data: jsonObjects,
                    dataType: 'text',
                    success: function (response) {
                        if (response) {
                            swal.fire({
                                title: 'Successful!',
                                text: 'Subtitle Deleted Successfully!',
                                icon: 'success',
                                showCancelButton: false,
                                confirmButtonColor: '#556ee6',
                                cancelButtonColor: "#f46a6a"
                            }).then(function () {
                                location.reload();
                            });
                        } else {
                            swal.fire({
                                title: 'Error',
                                text: 'Something Went Wrong :(',
                                icon: 'error'
                            }).then(function () {
                                location.reload();
                            });
                        }

                    }
                });
            }
        });
    }

</script>