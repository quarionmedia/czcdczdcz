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

            						<h4 class="font-size-18">Report Manager</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Miscellaneous</a></li>

            							<li class="breadcrumb-item active">Report Manager</li>

            						</ol>

            					</div>

            				</div>

            			</div>

            			<!-- end page title -->

                        <div class="row">

                        	<div class="col-12">

                        		<div class="card">

                        			<div class="card-body">
                                        <div class="table-responsive">
                        				    <table id="datatable" class="table table-striped"
                        				    	style="border-collapse: collapse; border-spacing: 0; width: 100%;">
    
                        				    	<thead>
    
                        				    		<tr>
    
                        				    			<th>#</th>
    
                                                        <th>User</th>
    
                        				    			<th>Title</th>
    
                        				    			<th>Description</th>
    
                        				    			<th>report_type</th>
    
                        				    			<th>Status</th>
    
                        				    		</tr>
    
                        				    	</thead>
    
                        				    </table>
                        			    </div>
                                    </div>
                        		</div>

                        	</div> <!-- end col -->

                        </div> <!-- end row -->
            			

            		</div> <!-- container-fluid -->

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
            "processing": true,
            "serverSide": true,
            "ajax": '<?= site_url('Admin_api/get_all_report') ?>',
            "columns": [{
                    "data": "1",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {
                    "data": "3",
                    render: function (data, type, row, meta) {
                        return `<div class="d-flex align-items-center">
                                        <div class="media-support-info">
                                        <h5 class="iq-sub-label">`+data+`</h5>
                                        <p class="mb-0">`+row['4']+`</p>
                                        </div>
                                    </div>
                        `;
                    }
                    
                },
                {
                    "data": "5"
                },
                {
                    "data": "6"
                },
                {
                    "data": "7",
                    render: function (data) {
                        if (data == 0) {
                            return '<span class="badge bg-danger">Custom</span>';
                        } else if (data == 1) {
                            return '<span class="badge bg-primary">Movie</span>';
                        } else if (data == 2) {
                            return '<span class="badge bg-info">Web Series</span>';
                        } else if (data == 3) {
                            return '<span class="badge bg-warning">Live TV</span>';
                        }
                    }
                },
                {
                    "data": "8",
                    render: function (data, type, row, meta) {
                        if (data == 0) {
                            return '<div class="btn-group mr-1 mt-2"> <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true"  aria-expanded="false">Pending</button> <div class="dropdown-menu" style=""> <a class="dropdown-item" onclick="UpdateStatus(0,' +
                            row["2"] + ')" href="#">Pending</a> <a class="dropdown-item" onclick="UpdateStatus(1,' +
                            row["2"] + ')" href="#">Solved</a> <a class="dropdown-item" onclick="UpdateStatus(2,' +
                            row["2"] + ')" href="#">Canceled</a> <a class="dropdown-item" onclick="DeleteStatus(2,' +
                            row["2"] + ')" href="#">Delete</a> </div>';
                        } else if (data == 1) {
                            return '<div class="btn-group mr-1 mt-2"> <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true"  aria-expanded="false">Solved</button> <div class="dropdown-menu" style=""> <a class="dropdown-item" onclick="UpdateStatus(0,' +
                            row["2"] + ')" href="#">Pending</a> <a class="dropdown-item" onclick="UpdateStatus(1,' +
                            row["2"] + ')" href="#">Solved</a> <a class="dropdown-item" onclick="UpdateStatus(2,' +
                            row["2"] + ')" href="#">Canceled</a> <a class="dropdown-item" onclick="DeleteStatus(2,' +
                            row["2"] + ')" href="#">Delete</a> </div>';
                        }else if (data == 2) {
                            return '<div class="btn-group mr-1 mt-2"> <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true"  aria-expanded="false">Canceled</button> <div class="dropdown-menu" style=""> <a class="dropdown-item" onclick="UpdateStatus(0,' +
                            row["2"] + ')" href="#">Pending</a> <a class="dropdown-item" onclick="UpdateStatus(1,' +
                            row["2"] + ')" href="#">Solved</a> <a class="dropdown-item" onclick="UpdateStatus(2,' +
                            row["2"] + ')" href="#">Canceled</a> <a class="dropdown-item" onclick="DeleteStatus(2,' +
                            row["2"] + ')" href="#">Delete</a> </div>';
                        }
                    }
                }
            ]
        });

        function UpdateStatus(status, report_id) {
            Swal.fire({
              title: "Do you want to update report status?",
              showDenyButton: true,
              showCancelButton: true,
              confirmButtonText: "With Notification",
              denyButtonText: `Without Notification`
            }).then((result) => {
              if (result.isConfirmed) {
                changeStatus(1, status, report_id);
              } else if (result.isDenied) {
                changeStatus(0, status, report_id);
              }
            });
        }

        function changeStatus(notify, status, report_id) {
            $.ajax({
                type: 'POST',
                url: '<?= site_url('Admin_api/update_report_status') ?>',
                data: {
                    notify: notify,
                    report_id: report_id,
	                status: status
                },
                dataType: 'text',
                success: function (response) {
                    if (response) {
                        swal.fire({
                            title: 'Successful!',
                            text: 'Status Updated successfully!',
                            icon: 'success',
                            showCancelButton: false,
                            confirmButtonColor: '#556ee6',
                            cancelButtonColor: "#f46a6a"
                        }).then(function() {
                            location.reload();
                        });
                    } else {
                        swal.fire({
                            title: 'Error!',
                            text: 'Something Went Wrong!',
                            icon: 'error',
                            showCancelButton: false,
                            confirmButtonColor: '#556ee6',
                            cancelButtonColor: "#f46a6a"
                        }).then(function() {
                            location.reload();
                        });
                    }
                }
            });
        }

        function DeleteStatus(status, report_id) {
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
                    $.ajax({
                        type: 'POST',
                        url: '<?= site_url('Admin_api/delete_report') ?>',
                        data: {
                            report_id: report_id
                        },
                        dataType: 'text',
                        success: function (response) {
                            if (response) {
                                swal.fire({
                                    title: 'Successful!',
                                    text: 'Report Deleted successfully!',
                                    icon: 'success',
                                    showCancelButton: false,
                                    confirmButtonColor: '#556ee6',
                                    cancelButtonColor: "#f46a6a"
                                }).then(function() {
                                    location.reload();
                                });
                            } else {
                                swal.fire({
                                    title: 'Error!',
                                    text: 'Something Went Wrong!',
                                    icon: 'error',
                                    showCancelButton: false,
                                    confirmButtonColor: '#556ee6',
                                    cancelButtonColor: "#f46a6a"
                                }).then(function() {
                                    location.reload();
                                });
                            }
    
                        }
                    });
                }
            });
        }

    </script>