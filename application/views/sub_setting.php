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

            						<h4 class="font-size-18">Subscription Setting</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Subscription Setting</a></li>

            							<li class="breadcrumb-item active">Setting</li>

            						</ol>

            					</div>

            				</div>

            			</div>

            			<!-- end page title -->

						<div class="form" method="post">

                        	<div class="row">

                        		<div class="col-md-12">

                        			<div class="card card-body">
									    <h4 class="card-title mt-0">Payment Gateway Settings</h4>
                        			    <hr>
                        				<div class="form-group row mb-3 mt-3">
                        					<label class="col-sm-3 control-label">Payment Gateway Type</label>
                        					<div class="col-sm-3 ">
                        						<select class="form-control form-select" id="payment_gateway_type"
                        							name="payment_gateway_type">
                        							<option value="0" selected="">Payment Gateways</option>
                        							<option value="1">Custom Gateways</option>
                        						</select>
                        					</div>
                        				</div>

										<div class="form-group mb-3">

                        			            <div class="col-md-12 row justify-content-end">

                        			                <button
                        			                    class="btn btn-primary dropdown-toggle waves-effect waves-light col-md-2"
                        			                    onclick="updatePaymentGatewayType()" id="create_btn" type="submit"
                        			                    aria-haspopup="true" aria-expanded="false">

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
        $( document ).ready(function() {
			$('#payment_gateway_type').val('<?php echo $config->payment_gateway_type; ?>');
        });

        function updatePaymentGatewayType() {
			var payment_gateway_type = document.getElementById("payment_gateway_type").value;
			var jsonObjects = {
                payment_gateway_type: payment_gateway_type
            };
            $.ajax({
                type: 'POST',
                url: '<?= site_url('Admin_api/updatePaymentGatewayType') ?>',
                data: jsonObjects,
                dataType: 'text',
                success: function (response) {
                    if (response) {
                        swal.fire({
                            title: 'Successful!',
                            text: 'Payment Gateway Type Updated successfully!',
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
    </script>