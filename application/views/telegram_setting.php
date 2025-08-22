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

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Telegram Notifications</a></li>

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

                        				<h3 class="card-title mt-0">TELEGRAM NOTIFICATION SETTING</h3>

                        				<hr>



                        				<div class="alert alert-warning">If you don't have <a
                        						href="https://telegram.org/" target="_blank">Telegram</a> account
                        					yet.Signup <a href="https://telegram.org/" target="_blank">here</a> to get
                        					Bot Token And Chat ID.

                        				</div>

                        				<br>



                        				<div class="form-group mb-3">

                        					<label>Telegram Bot Token</label>

                        					<input class="form-control col-md-9" type="text" value="<?php echo $config->telegram_token ?>"
                        						id="telegram_bot_token">

                        				</div>



                        				<div class="form-group mb-3">

                        					<label>Telegram Chat ID</label>

                        					<input class="form-control col-md-9" type="text" value="<?php echo $config->telegram_chat_id ?>"
                        						id="teligram_chat_id" placeholder="@telegram">

                        				</div>



                        				<div class="form-group mb-3">

                        					<div class="col-md-12 row justify-content-end">

                        						<button class="btn btn-primary dropdown-toggle waves-effect waves-light col-md-2"
                        							onclick="Save_Teligram_Data()" id="create_btn" type="submit"
                        							aria-haspopup="true" aria-expanded="false">

                        							<i class="mdi mdi-content-save-all"></i> SAVE CHANGES

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
        function Save_Teligram_Data() {
            var telegram_bot_token = document.getElementById("telegram_bot_token").value;
            var teligram_chat_id = document.getElementById("teligram_chat_id").value;

            var jsonObjects = {
                telegram_bot_token: telegram_bot_token,
                teligram_chat_id: teligram_chat_id,
            };

            $.ajax({
                type: 'POST',
                url: '<?= site_url('Admin_api/save_telegram_data') ?>',
                data: jsonObjects,
                dataType: 'text',
                success: function (response) {
                    if (response) {
                        swal.fire({
                            title: 'Successful!',
                            text: 'Telegram Data Updated successfully!',
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