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

            						<h4 class="font-size-18">Push Notification</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Notifications</a></li>

            							<li class="breadcrumb-item active">Settings</li>

            						</ol>

            					</div>

            				</div>

            			</div>

            			<!-- end page title -->


                        <div class="form" action="" method="post">

                    <div class="row">

                        <div class="col-md-12">

                            <div class="card card-body">

                                <h3 class="card-title mt-0">PUSH NOTIFICATION SETTING</h3>

                                <hr>



                                <div class="alert alert-warning">If you don't have <a href="https://onesignal.com/"

                                        target="_blank">OneSignal</a> account yet.Signup <a

                                        href="https://onesignal.com/" target="_blank">here</a> to get AppID And Key.

                                </div>

                                <br>


                                <div class="form-group  mb-3">

                                    <label>Onesignal App ID</label>

                                    <input class="form-control col-md-9" type="text" value="<?php echo $config->onesignal_appid ?>" id="Onesignal_Appid">

                                </div>

                                <div class="form-group  mb-3">

                                    <label>Onesignal Api Key</label>

                                    <input class="form-control col-md-9" type="text" value="<?php echo $config->onesignal_api_key ?>" id="Onesignal_Api_Key">

                                </div>


                                <div class="form-group mb-3 row justify-content-end">

                                    <div class="col-md-1">

                                        <button class="btn btn-primary dropdown-toggle waves-effect waves-light"

                                            onclick="Save_Onesignal_Data()" id="create_btn"

                                            type="submit" aria-haspopup="true" aria-expanded="false">

                                            <i class="mdi mdi-content-save-all"></i> SAVE

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
        var base_url = window.location.origin;
        
        function copyToClipboard(element) {
            var copyText = document.getElementById(element);
            copyText.select();
            copyText.setSelectionRange(0, 99999);
            document.execCommand("copy");
            swal.fire({
                title: 'Copied!',
                text: copyText.value + '\nNow just paste into android configuration file',
                icon: 'success'
            });
        }


        function Save_Onesignal_Data() {

            var Onesignal_Api_Key = document.getElementById("Onesignal_Api_Key").value;
            var Onesignal_Appid = document.getElementById("Onesignal_Appid").value;

            var jsonObjects = {
                Onesignal_Api_Key: Onesignal_Api_Key,
                Onesignal_Appid: Onesignal_Appid,
            };
            
            $.ajax({
                type: 'POST',
                url: '<?= site_url('Admin_api/save_onesignal_data') ?>',
                data: jsonObjects,
                dataType: 'text',
                success: function (response) {
                    if (response) {
                        swal.fire({
                            title: 'Successful!',
                            text: 'Onesignal Data Updated successfully!',
                            icon: 'success',
                            showCancelButton: false,
                            confirmButtonColor: '#556ee6',
                            cancelButtonColor: "#f46a6a"
                        }).then(function() {
                            location.reload();
                        });
                    } else {
                        swal.fire({
                            title: 'Error',
                            text: 'Something Went Wrong :(',
                            icon: 'error'
                        }).then(function() {
                            location.reload();
                        });
                    }
                }
            })
            
        }

    </script>