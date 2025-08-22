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

            						<h4 class="font-size-18">Subscription Requests</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Subscription</a></li>

            							<li class="breadcrumb-item active">Subscription Requests</li>

            						</ol>

            					</div>

            				</div>

            			</div>

            			<!-- end page title -->


                        <div class="form" method="post">

                        	<div class="row">
                        		<div class="col-md-12">
                        			<div class="card card-body">
                        				<div class="panel-heading">

                        					<div
                        						class="d-flex justify-content-between align-items-center flex-wrap grid-margin">
                        						<div>
                        							<h4 class="mb-3 mb-md-0">Subscription Requests</h4>
                        						</div>
                        					</div>

                        				</div>

                        				<br>

                        				<div class="panel-body">
                                            <div class="table-responsive">
                        						<table id="datatable" class="table table-striped"
                        							style="border-collapse: collapse; border-spacing: 0; width: 100%;">

                        							<thead>

                        								<tr role="row">
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 9px;">#</th>
                                                            <th class="sorting_disabled" rowspan="1" colspan="1"
                                                                style="width: 68px;">Action</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 50px;">User</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 35px;">Payment Type</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 57px;">Payment Details</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 56px;">Subscription Name</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Subscription Type</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Subscription Time</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Subscription Amount</th>
                                                            <th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Uploaded Image</th>
                                                            <th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Request Status</th>
                        								</tr>

                        							</thead>

                        						</table>
                        					</div>
                        				</div>

                        			</div>
                        		</div>
                        	</div>

                        </div>
            			

            		</div> <!-- container-fluid -->

                    <div class="modal fade" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title" id="myModalLabel">Preview</h4>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    
                                </div>
                                <div class="modal-body">
                                    <img src="" id="imagepreview" class="img-fluid">
                                </div>
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
            "processing": true,
            "serverSide": true,
            "info": false,
            "filter": false,
            "paging":   false,
            "pageLength": 100,
            "ajax": '<?= site_url('Admin_api/get_all_custom_payment_requests') ?>',
            "columns": [{
                    "data": "1",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {
                    "data": "12",
                    render: function (data, type, row, meta) {
                        return `
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Options</button>
                            <div class="dropdown-menu" style="">
                                <!--<a class="dropdown-item" onclick="updateRequestStatus(` + data + `,` +  row['11'] + `, 0)" href="#">Pending</a>-->
                                <a class="dropdown-item" onclick="updateRequestStatus(` + data + `,` +  row['11'] + `, 1)" href="#">Approved</a>
                                <a class="dropdown-item" onclick="updateRequestStatus(` + data + `,` +  row['11'] + `, 2)" href="#">Declined</a>
                            </div>
                        </div>`;
                    }
                },
                {
                    "data": "2",
                    render: function (data, type, row, meta) {
                        return `
                        <div class="d-flex align-items-center">
                                            <div class="media-support-info">
                                            <h5 class="iq-sub-label">`+data+`</h5>
                                            <p class="mb-0">`+row['13']+`</p>
                                            </div>
                                        </div>
                        `;
                    }
                },
                {
                    "data": "3"
                },
                {
                    "data": "4"
                },
                {
                    "data": "5"
                },
                {
                    "data": "6",
                    render: function (data) {
                        var digits = (""+data).split("");
                        var sub_data = "";
                        digits.forEach(function(digit) {
                            if(digit==0) {
                                sub_data = sub_data+'<i class="fa fa-solid fa-chevron-right"></i> Default<br>'
                            } else if(digit==1) {
                                sub_data = sub_data+'<i class="fa fa-solid fa-chevron-right"></i> Remove Ads<br>'
                            } else if(digit==2) {
                                sub_data = sub_data+'<i class="fa fa-solid fa-chevron-right"></i> Play Premium<br>'
                            } else if(digit==3) {
                                sub_data = sub_data+'<i class="fa fa-solid fa-chevron-right"></i> Download Premium<br>'
                            }
                        });
                        return sub_data;
                    }
                },
                {
                    "data": "7",
                    render: function (data) {
                        return data+" Days";
                    }
                },
                {
                    "data": "8",
                    render: function (data, type, row, meta) {
                        if (row['9'] == 0) {
                            return data+' INR';
                        } else if (row['9'] == 1) {
                            return data+' USD';
                        }
                    }
                },
                {
                    "data": "10",
                    render: function (data) {
                       return `
                       <a class="btn btn-info btn-icon btn-sm rounded-pill ms-2" onclick="showImagePreview('/uploads/images/payment_requests/`+data+`')" role="button">
                                <span class="btn-inner">
                                      <svg width="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                          <path d="M21.9999 14.7024V16.0859C21.9999 16.3155 21.9899 16.5471 21.9699 16.7767C21.6893 19.9357 19.4949 22 16.3286 22H7.67126C6.06806 22 4.71535 21.4797 3.74341 20.5363C3.36265 20.1864 3.042 19.7753 2.7915 19.3041C3.12217 18.9021 3.49291 18.462 3.85363 18.0208C4.46485 17.289 5.05603 16.5661 5.42677 16.0959C5.97788 15.4142 7.43078 13.6196 9.44481 14.4617C9.85563 14.6322 10.2164 14.8728 10.547 15.0833C11.3586 15.6247 11.6993 15.7851 12.2705 15.4743C12.9017 15.1335 13.3125 14.4617 13.7434 13.76C13.9739 13.388 14.2043 13.0281 14.4548 12.6972C15.547 11.2736 17.2304 10.8926 18.6332 11.7348C19.3346 12.1559 19.9358 12.6872 20.4969 13.2276C20.6172 13.3479 20.7374 13.4592 20.8476 13.5695C20.9979 13.7198 21.4989 14.2211 21.9999 14.7024Z" fill="currentColor"></path>
                                          <path opacity="0.4" d="M16.3387 2H7.67134C4.27455 2 2 4.37607 2 7.91411V16.086C2 17.3181 2.28056 18.4119 2.79158 19.3042C3.12224 18.9022 3.49299 18.4621 3.85371 18.0199C4.46493 17.2891 5.05611 16.5662 5.42685 16.096C5.97796 15.4143 7.43086 13.6197 9.44489 14.4618C9.85571 14.6323 10.2164 14.8729 10.5471 15.0834C11.3587 15.6248 11.6994 15.7852 12.2705 15.4734C12.9018 15.1336 13.3126 14.4618 13.7435 13.759C13.9739 13.3881 14.2044 13.0282 14.4549 12.6973C15.5471 11.2737 17.2305 10.8927 18.6333 11.7349C19.3347 12.1559 19.9359 12.6873 20.497 13.2277C20.6172 13.348 20.7375 13.4593 20.8477 13.5696C20.998 13.7189 21.499 14.2202 22 14.7025V7.91411C22 4.37607 19.7255 2 16.3387 2Z" fill="currentColor"></path>
                                          <path d="M11.4543 8.79668C11.4543 10.2053 10.2809 11.3783 8.87313 11.3783C7.46632 11.3783 6.29297 10.2053 6.29297 8.79668C6.29297 7.38909 7.46632 6.21509 8.87313 6.21509C10.2809 6.21509 11.4543 7.38909 11.4543 8.79668Z" fill="currentColor"></path>
                                      </svg>
                                </span>
                            </a>
                       `;
                    }
                },
                {
                    "data": "11",
                    render: function (data) {
                        if (data == 0) {
                            return '<span class="badge bg-warning">Pending</span>';
                        } else if (data == 1) {
                            return '<span class="badge bg-success">Approved</span>';
                        } else if (data == 2) {
                            return '<span class="badge bg-danger">Declined</span>';
                        }
                    }
                }
            ]
        });
        function showImagePreview(imageSRC) {
            $('#imagepreview').attr('src', imageSRC);
            $('#imagemodal').modal('show');
        }

        function updateRequestStatus(ID, current_status, request_status) {
            if(current_status == 1) {
                toastr.options = {
                  "closeButton": false,
                  "debug": false,
                  "newestOnTop": true,
                  "progressBar": false,
                  "positionClass": "toast-top-right",
                  "preventDuplicates": false,
                  "onclick": null,
                  "showDuration": "300",
                  "hideDuration": "2000",
                  "timeOut": "2000",
                  "extendedTimeOut": "1000",
                  "showEasing": "swing",
                  "hideEasing": "linear",
                  "showMethod": "fadeIn",
                  "hideMethod": "fadeOut"
                };
                toastr.success("Subscription Request Already approved!");
            } else if(current_status == 2) {
                toastr.options = {
                  "closeButton": false,
                  "debug": false,
                  "newestOnTop": true,
                  "progressBar": false,
                  "positionClass": "toast-top-right",
                  "preventDuplicates": false,
                  "onclick": null,
                  "showDuration": "300",
                  "hideDuration": "2000",
                  "timeOut": "2000",
                  "extendedTimeOut": "1000",
                  "showEasing": "swing",
                  "hideEasing": "linear",
                  "showMethod": "fadeIn",
                  "hideMethod": "fadeOut"
                };
                toastr.success("Subscription Request Already Declined!");
            } else {
                Swal.fire({
                    title: 'Please Wait',
                    allowEscapeKey: false,
                    allowOutsideClick: false,
                    showConfirmButton: false,
                    onOpen: ()=>{
                        Swal.showLoading();
                    },
                    onClose: ()=>{
                        
                    }
                });
                var jsonObjects = {
                    ID: ID,
                    request_status: request_status
                };
                $.ajax({
                    type: 'POST',
                    url: '<?= site_url('Admin_api/updateRequestStatus') ?>',
                    data: jsonObjects,
                    dataType: 'text',
                    success: function (response) {
                        Swal.close();
                        if (response != "") {
                            $('#datatable').DataTable().ajax.reload();
                        }
                    }
                });
            }
        }

    </script>