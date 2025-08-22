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

            						<h4 class="font-size-18">Telegram Announcement</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Telegram Notifications</a></li>

            							<li class="breadcrumb-item active">Telegram Announcement</li>

            						</ol>

            					</div>

            				</div>

            			</div>

            			<!-- end page title -->


                        <div class="form" action="https://admin.fliq.pro/admin/videos/add/" method="post">
                        	<div class="row">
                        		<div class="col-md-12">
                        			<div class="card card-body">
                        				<h3 class="card-title mt-0">SEND TELEGRAM NOTIFICATION (Announcement)</h3>
                        				<hr>

                        				<div class="form-group mb-3">
                        					<label>Heading</label>
                        					<input class="form-control col-md-9" type="text" value="" id="Heading">
                        				</div>

                        				<div class="form-group mb-3">
                        					<label>Message</label>
                        					<div>
                        						<textarea required="" class="form-control col-md-9" id="Message"
                        							rows="5"></textarea>
                        					</div>
                        				</div>

                        				<div class="form-group mb-3">
                        					<label>Image</label>
                        					<input class="form-control col-md-9" type="text" value="" id="image">
                        				</div>

                        				<div class="form-group mb-3">
                        					<div class="col-md-12 row justify-content-end">
                        						<button class="btn btn-primary dropdown-toggle waves-effect waves-light col-md-2"
                        							onclick="Save_Telegram_Data()" id="create_btn" type="submit"
                        							aria-haspopup="true" aria-expanded="false">
                        							<i class="ion ion-md-send"></i> SEND
                        						</button>
                        					</div>
                        				</div>
                        			</div>
                        		</div>
                        	</div>
                        </div>
            			

            		</div> <!-- container-fluid -->

            	</div>


            	<?php include("partials/footer_rights.php"); ?>


            </div>
            <!-- end main content-->

        </div>
        <!-- END layout-wrapper -->

        <?php include("partials/footer.php"); ?>

    <script>
        function Save_Telegram_Data() {
            var Heading = document.getElementById("Heading").value;
            var Message = document.getElementById("Message").value;
            var image = document.getElementById("image").value;
    
            if(Heading != "" && Message != "") {
                var jsonObjects = {
                    telegram_token: '<?php echo $config->telegram_token; ?>',
	                telegram_chat_id: '<?php echo $config->telegram_chat_id; ?>',
	                Heading: Heading,
                    Message: Message,
	                image: image
                };
    
                $.ajax({
                    type: 'POST',
                    url: '<?= site_url('Admin_api/teligram') ?>',
                    data: jsonObjects,
                    dataType: 'text',
                    success: function (response) {
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
                        toastr.success("Sended Successfully!");
                    },
                    error: function (response) {
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
                        toastr.error("Something Went Wrong!");
                    }
                })
            } else {
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
                toastr.warning("Fill All Details Correctly!");
            }
        }
    </script>