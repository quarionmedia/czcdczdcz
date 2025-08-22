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
						<div class="page-title-box">
							<div class="row align-items-center">
								<div class="col-md-8">
									<div class="page-title-box">
										<h4 class="font-size-18">Subscription Setting</h4>
										<ol class="breadcrumb mb-0">
											<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>
											<li class="breadcrumb-item"><a href="javascript: void(0);">Subscription Setting</a></li>
											<li class="breadcrumb-item active">Payment Gateways</li>
										</ol>
									</div>
								</div>
								<div class="col-md-4">
									<div class="float-end d-none d-md-block">
										<button class="btn btn-primary dropdown-toggle waves-effect waves-light"
											onclick="Save_Subscription_Setting_Data()" id="create_btn" type="submit" aria-haspopup="true"
											aria-expanded="false">
											<i class="mdi mdi-content-save-all"></i> SAVE
										</button>
									</div>
								</div>
							</div>
						</div>

            			<!-- end page title -->
						<div class="row">
							<div class="col-lg-12">
								<div class="card">
									<div class="card-body">

										<h4 class="card-title">Payment Gateways</h4>
										<p class="card-title-desc">Modify as you need</p>

										<!-- Nav tabs -->
										<ul class="nav nav-tabs nav-tabs-custom mb-3" role="tablist">
											<li class="nav-item" role="presentation">
												<a class="nav-link active" data-bs-toggle="tab" href="#razorpay" role="tab"
													aria-selected="true" tabindex="0">
													<span class="d-none d-md-block">Razorpay</span><span class="d-block d-md-none"><i
															class="far fa-credit-card h5"></i></span>
												</a>
											</li>
											<li class="nav-item" role="presentation">
												<a class="nav-link" data-bs-toggle="tab" href="#paypal" role="tab" aria-selected="false"
													tabindex="1">
													<span class="d-none d-md-block">Paypal</span><span class="d-block d-md-none"><i
															class="fab fa-paypal h5"></i></span>
												</a>
											</li>
											<li class="nav-item" role="presentation">
												<a class="nav-link" data-bs-toggle="tab" href="#flutterwave" role="tab"
													aria-selected="false" tabindex="2">
													<span class="d-none d-md-block">Flutterwave</span><span class="d-block d-md-none"><i
															class="far fa-credit-card h5"></i></span>
												</a>
											</li>
											<li class="nav-item" role="presentation">
												<a class="nav-link" data-bs-toggle="tab" href="#uddoktapay" role="tab"
													aria-selected="false" tabindex="3">
													<span class="d-none d-md-block">Uddoktapay</span><span class="d-block d-md-none"><i
															class="far fa-credit-card h5"></i></span>
												</a>
											</li>
											<li class="nav-item" role="presentation">
												<a class="nav-link" data-bs-toggle="tab" href="#bkash" role="tab" aria-selected="false"
													tabindex="4">
													<span class="d-none d-md-block">bKash</span><span class="d-block d-md-none"><i
															class="far fa-credit-card h5"></i></span>
												</a>
											</li>
                                            <li class="nav-item" role="presentation">
                                                <a class="nav-link" data-bs-toggle="tab" href="#google_play_billing" role="tab" aria-selected="false"
                                                   tabindex="5">
                                                    <span class="d-none d-md-block">Google Play Billing</span><span class="d-block d-md-none"><i
                                                                class="far fa-credit-card h5"></i></span>
                                                </a>
                                            </li>
                                            <li class="nav-item" role="presentation">
                                                <a class="nav-link" data-bs-toggle="tab" href="#stripe" role="tab" aria-selected="false"
                                                   tabindex="5">
                                                    <span class="d-none d-md-block">Stripe</span><span class="d-block d-md-none"><i
                                                                class="far fa-credit-card h5"></i></span>
                                                </a>
                                            </li>
                                            <li class="nav-item" role="presentation">
                                                <a class="nav-link" data-bs-toggle="tab" href="#coingate" role="tab" aria-selected="false"
                                                   tabindex="5">
                                                    <span class="d-none d-md-block">Coingate</span><span class="d-block d-md-none"><i
                                                                class="far fa-credit-card h5"></i></span>
                                                </a>
                                            </li>
										</ul>

										<!-- Tab panes -->
										<div class="tab-content" id="setting_tabs">
											<div class="tab-pane p-3 active show" id="razorpay" role="tabpanel">
												<div class="form-group row mb-3">
													<label class="control-label col-sm-3 ">Enable Razorpay</label>
													<div class="col-sm-6">
														<input type="checkbox" id="razorpay_status" switch="bool">
														<label for="razorpay_status" data-on-label="" data-off-label=""></label>
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Razorpay key id</label>
													<div class="col-sm-6">
														<input type="text" name="razorpay_key_id" id="razorpay_key_id" placeholder=""
															class="form-control" required=""
															value="<?php echo $config->razorpay_key_id ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Razorpay key secret</label>
													<div class="col-sm-6">
														<input type="text" name="razorpay_key_secret" id="razorpay_key_secret"
															placeholder="" class="form-control" required=""
															value="<?php echo $config->razorpay_key_secret ?>">
													</div>
												</div>
											</div>

											<div class="tab-pane p-3" id="paypal" role="tabpanel">
												<div class="form-group row mb-3">
													<label class="control-label col-sm-3 ">Enable Paypal</label>
													<div class="col-sm-6">
														<input type="checkbox" id="paypal_status" switch="bool">
														<label for="paypal_status" data-on-label="" data-off-label=""></label>
													</div>
												</div>
												<div class="form-group row mb-3 mt-3">
													<label class="col-sm-3 control-label">Paypal Payment Type</label>
													<div class="col-sm-3 ">
														<select class="form-control form-select" id="paypal_payment_type"
															name="paypal_payment_type">
															<option value="0" selected="">SandBox</option>
															<option value="1">Live</option>
														</select>
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Paypal Clint ID</label>
													<div class="col-sm-6">
														<input type="text" name="paypal_clint_id" id="paypal_clint_id" placeholder=""
															class="form-control" required=""
															value="<?php echo $config->paypal_clint_id ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Paypal Secret Key</label>
													<div class="col-sm-6">
														<input type="text" name="paypal_secret_key" id="paypal_secret_key" placeholder=""
															class="form-control" required=""
															value="<?php echo $config->paypal_secret_key ?>">
													</div>
												</div>
											</div>

											<div class="tab-pane p-3" id="flutterwave" role="tabpanel">
												<div class="form-group row mb-3">
													<label class="control-label col-sm-3 ">Enable Flutterwave</label>
													<div class="col-sm-6">
														<input type="checkbox" id="flutterwave_status" switch="bool">
														<label for="flutterwave_status" data-on-label="" data-off-label=""></label>
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Flutterwave Public Key</label>
													<div class="col-sm-6">
														<input type="text" name="flutterwave_public_key" id="flutterwave_public_key"
															placeholder="" class="form-control" required=""
															value="<?php echo $config->flutterwave_public_key ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Flutterwave Secret Key</label>
													<div class="col-sm-6">
														<input type="text" name="flutterwave_secret_key" id="flutterwave_secret_key"
															placeholder="" class="form-control" required=""
															value="<?php echo $config->flutterwave_secret_key ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Flutterwave Encryption Key</label>
													<div class="col-sm-6">
														<input type="text" name="flutterwave_encryption_key"
															id="flutterwave_encryption_key" placeholder="" class="form-control"
															required="" value="<?php echo $config->flutterwave_encryption_key ?>">
													</div>
												</div>
											</div>

											<div class="tab-pane p-3" id="uddoktapay" role="tabpanel">
												<div class="form-group row mb-3">
													<label class="control-label col-sm-3 ">Enable Uddoktapay</label>
													<div class="col-sm-6">
														<input type="checkbox" id="uddoktapay_status" switch="bool">
														<label for="uddoktapay_status" data-on-label="" data-off-label=""></label>
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Uddoktapay Api Key</label>
													<div class="col-sm-6">
														<input type="text" name="uddoktapay_api_key" id="uddoktapay_api_key"
															placeholder="" class="form-control" required=""
															value="<?php echo $config->uddoktapay_api_key ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">Uddoktapay Base URL</label>
													<div class="col-sm-6">
														<input type="text" name="uddoktapay_base_url" id="uddoktapay_base_url"
															placeholder="" class="form-control" required=""
															value="<?php echo $config->uddoktapay_base_url ?>">
													</div>
												</div>

                                                <div class="form-group row mb-3">
                                                    <label class="col-sm-3 control-label">Uddoktapay Exchange Rate (0 = No Exchange)</label>
                                                    <div class="col-sm-6">
                                                        <input type="number" name="uddoktapay_exchange_rate" id="uddoktapay_exchange_rate"
                                                               placeholder="" class="form-control" required=""
                                                               value="<?php echo $config->uddoktapay_exchange_rate ?>">
                                                    </div>
                                                </div>
											</div>

											<div class="tab-pane p-3" id="bkash" role="tabpanel">
												<div class="form-group row mb-3">
													<label class="control-label col-sm-3 ">Enable bKash</label>
													<div class="col-sm-6">
														<input type="checkbox" id="bKash_status" switch="bool">
														<label for="bKash_status" data-on-label="" data-off-label=""></label>
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">bKash App Key</label>
													<div class="col-sm-6">
														<input type="text" name="bKash_app_key" id="bKash_app_key" placeholder=""
															class="form-control" required="" value="<?php echo $config->bKash_app_key ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">bKash App Secret</label>
													<div class="col-sm-6">
														<input type="text" name="bKash_app_secret" id="bKash_app_secret" placeholder=""
															class="form-control" required=""
															value="<?php echo $config->bKash_app_secret ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">bKash Username</label>
													<div class="col-sm-6">
														<input type="text" name="bKash_username" id="bKash_username" placeholder=""
															class="form-control" required=""
															value="<?php echo $config->bKash_username ?>">
													</div>
												</div>
												<div class="form-group row mb-3">
													<label class="col-sm-3 control-label">bKash Password</label>
													<div class="col-sm-6">
														<input type="text" name="bKash_password" id="bKash_password" placeholder=""
															class="form-control" required=""
															value="<?php echo $config->bKash_password ?>">
													</div>
												</div>
												<div class="form-group row mb-3 mt-3">
													<label class="col-sm-3 control-label">bKash Payment Type</label>
													<div class="col-sm-3 ">
														<select class="form-control form-select" id="bKash_payment_type"
															name="bKash_payment_type">
															<option value="0" selected="">SandBox</option>
															<option value="1">Live</option>
														</select>
													</div>
												</div>

                                                <div class="form-group row mb-3">
                                                    <label class="col-sm-3 control-label">bKash Exchange Rate (0 = No Exchange)</label>
                                                    <div class="col-sm-6">
                                                        <input type="number" name="bkash_exchange_rate" id="bkash_exchange_rate"
                                                               placeholder="" class="form-control" required=""
                                                               value="<?php echo $config->bkash_exchange_rate ?>">
                                                    </div>
                                                </div>
											</div>

                                            <div class="tab-pane p-3" id="google_play_billing" role="tabpanel">
                                                <div class="form-group row mb-3">
                                                    <label class="control-label col-sm-3 ">Enable Google Play Billing</label>
                                                    <div class="col-sm-6">
                                                        <input type="checkbox" id="google_play_billing_status" switch="bool">
                                                        <label for="google_play_billing_status" data-on-label="" data-off-label=""></label>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane p-3" id="stripe" role="tabpanel">
                                                <div class="form-group row mb-3">
                                                    <label class="control-label col-sm-3 ">Enable Stripe</label>
                                                    <div class="col-sm-6">
                                                        <input type="checkbox" id="stripe_status" switch="bool">
                                                        <label for="stripe_status" data-on-label="" data-off-label=""></label>
                                                    </div>
                                                </div>
                                                <div class="form-group row mb-3">
                                                    <label class="col-sm-3 control-label">Stripe Publishable Key </label>
                                                    <div class="col-sm-6">
                                                        <input type="text" name="stripe_publishable_key" id="stripe_publishable_key"
                                                               placeholder="" class="form-control" required=""
                                                               value="<?php echo $config->stripe_publishable_key ?>">
                                                    </div>
                                                </div>
                                                <div class="form-group row mb-3">
                                                    <label class="col-sm-3 control-label">Stripe Secret Key</label>
                                                    <div class="col-sm-6">
                                                        <input type="text" name="stripe_secret_key" id="stripe_secret_key"
                                                               placeholder="" class="form-control" required=""
                                                               value="<?php echo $config->stripe_secret_key ?>">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane p-3" id="coingate" role="tabpanel">
                                                <div class="form-group row mb-3">
                                                    <label class="control-label col-sm-3 ">Enable Coingate</label>
                                                    <div class="col-sm-6">
                                                        <input type="checkbox" id="coingate_status" switch="bool">
                                                        <label for="coingate_status" data-on-label="" data-off-label=""></label>
                                                    </div>
                                                </div>
                                                <div class="form-group row mb-3 mt-3">
                                                    <label class="col-sm-3 control-label">Coingate Payment Environment</label>
                                                    <div class="col-sm-3 ">
                                                        <select class="form-control form-select" id="coingate_payment_environment"
                                                                name="coingate_payment_environment">
                                                            <option value="0" selected="">SandBox</option>
                                                            <option value="1">Live</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group row mb-3">
                                                    <label class="col-sm-3 control-label">Coingate Auth Token</label>
                                                    <div class="col-sm-6">
                                                        <input type="text" name="coingate_auth_token" id="coingate_auth_token"
                                                               placeholder="" class="form-control" required=""
                                                               value="<?php echo $config->coingate_auth_token ?>">
                                                    </div>
                                                </div>
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
			$('.nav-tabs a[href="#' + localStorage.getItem("currentTabIndex") + '"]').tab('show');
			$('.nav-tabs > li > a').click( function() {
                localStorage.setItem("currentTabIndex", $(this).attr('href').slice(1));
            });

            if ('<?php echo $config->razorpay_status; ?>' == 1) {
                document.getElementById("razorpay_status").checked = true;
            } else {
                document.getElementById("razorpay_status").checked = false;
			}

            if ('<?php echo $config->paypal_status; ?>' == 1) {
                document.getElementById("paypal_status").checked = true;
            } else {
                document.getElementById("paypal_status").checked = false;
			}

            if ('<?php echo $config->flutterwave_status; ?>' == 1) {
                document.getElementById("flutterwave_status").checked = true;
            } else {
                document.getElementById("flutterwave_status").checked = false;
			}
			
			if ('<?php echo $config->uddoktapay_status; ?>' == 1) {
                document.getElementById("uddoktapay_status").checked = true;
            } else {
                document.getElementById("uddoktapay_status").checked = false;
			}

			if ('<?php echo $config->bKash_status; ?>' == 1) {
                document.getElementById("bKash_status").checked = true;
            } else {
                document.getElementById("bKash_status").checked = false;
			}
            if ('<?php echo $config->google_play_billing_status; ?>' == 1) {
                document.getElementById("google_play_billing_status").checked = true;
            } else {
                document.getElementById("google_play_billing_status").checked = false;
			}
			$('#paypal_payment_type').val('<?php echo $config->paypal_type; ?>');
			$('#bKash_payment_type').val('<?php echo $config->bKash_payment_type; ?>');

            if ('<?php echo $config->stripe_status; ?>' == 1) {
                document.getElementById("stripe_status").checked = true;
            } else {
                document.getElementById("stripe_status").checked = false;
            }

            if ('<?php echo $config->coingate_status; ?>' == 1) {
                document.getElementById("coingate_status").checked = true;
            } else {
                document.getElementById("coingate_status").checked = false;
            }
            $('#coingate_payment_environment').val('<?php echo $config->coingate_payment_environment; ?>');
        });

        function Save_Subscription_Setting_Data() {
            if ($('#razorpay_status').is(':checked')) {
                var razorpay_status_int = 1;
            } else {
                var razorpay_status_int = 0;
            }
            var razorpay_key_id = document.getElementById("razorpay_key_id").value;
            var razorpay_key_secret = document.getElementById("razorpay_key_secret").value;
            if ($('#paypal_status').is(':checked')) {
                var paypal_status_int = 1;
            } else {
                var paypal_status_int = 0;
            }
			var paypal_payment_type = document.getElementById("paypal_payment_type").value;
            var paypal_clint_id = document.getElementById("paypal_clint_id").value;
			var paypal_secret_key = document.getElementById("paypal_secret_key").value;
            if ($('#flutterwave_status').is(':checked')) {
                var flutterwave_status = 1;
            } else {
                var flutterwave_status = 0;
            }
            var flutterwave_public_key = document.getElementById("flutterwave_public_key").value;
            var flutterwave_secret_key = document.getElementById("flutterwave_secret_key").value;
            var flutterwave_encryption_key = document.getElementById("flutterwave_encryption_key").value;

			
            if ($('#uddoktapay_status').is(':checked')) {
                var uddoktapay_status = 1;
            } else {
                var uddoktapay_status = 0;
            }
			var uddoktapay_api_key = document.getElementById("uddoktapay_api_key").value;
            var uddoktapay_base_url = document.getElementById("uddoktapay_base_url").value;

			if ($('#bKash_status').is(':checked')) {
                var bKash_status = 1;
            } else {
                var bKash_status = 0;
            }
            if ($('#google_play_billing_status').is(':checked')) {
                var google_play_billing_status = 1;
            } else {
                var google_play_billing_status = 0;
            }
			var bKash_app_key = document.getElementById("bKash_app_key").value;
            var bKash_app_secret = document.getElementById("bKash_app_secret").value;
			var bKash_username = document.getElementById("bKash_username").value;
            var bKash_password = document.getElementById("bKash_password").value;
			var bKash_payment_type = document.getElementById("bKash_payment_type").value;



            var uddoktapay_exchange_rate = document.getElementById("uddoktapay_exchange_rate").value;
            var bkash_exchange_rate = document.getElementById("bkash_exchange_rate").value;


            if ($('#stripe_status').is(':checked')) {
                var stripe_status = 1;
            } else {
                var stripe_status = 0;
            }
            var stripe_publishable_key = document.getElementById("stripe_publishable_key").value;
            var stripe_secret_key = document.getElementById("stripe_secret_key").value;

            if ($('#coingate_status').is(':checked')) {
                var coingate_status = 1;
            } else {
                var coingate_status = 0;
            }
            var coingate_payment_environment = document.getElementById("coingate_payment_environment").value;
            var coingate_auth_token = document.getElementById("coingate_auth_token").value;

            var jsonObjects = {
                razorpay_status_int: razorpay_status_int,
                razorpay_key_id: razorpay_key_id,
                razorpay_key_secret: razorpay_key_secret,
                paypal_status_int: paypal_status_int,
				paypal_payment_type: paypal_payment_type,
                paypal_clint_id: paypal_clint_id,
				paypal_secret_key: paypal_secret_key,
                flutterwave_status: flutterwave_status,
                flutterwave_public_key: flutterwave_public_key,
                flutterwave_secret_key: flutterwave_secret_key,
                flutterwave_encryption_key: flutterwave_encryption_key,
				uddoktapay_status: uddoktapay_status,
				uddoktapay_api_key: uddoktapay_api_key,
				uddoktapay_base_url: uddoktapay_base_url,
				bKash_status: bKash_status,
				bKash_app_key: bKash_app_key,
				bKash_app_secret: bKash_app_secret,
				bKash_username: bKash_username,
				bKash_password: bKash_password,
				bKash_payment_type: bKash_payment_type,
                google_play_billing_status: google_play_billing_status,
                uddoktapay_exchange_rate: uddoktapay_exchange_rate,
                bkash_exchange_rate: bkash_exchange_rate,
                stripe_status: stripe_status,
                stripe_publishable_key: stripe_publishable_key,
                stripe_secret_key: stripe_secret_key,
                coingate_status: coingate_status,
                coingate_payment_environment: coingate_payment_environment,
                coingate_auth_token: coingate_auth_token,
            };

            $.ajax({
                type: 'POST',
                url: '<?= site_url('Admin_api/update_sub_setting') ?>',
                data: jsonObjects,
                dataType: 'text',
                success: function (response) {
                    if (response) {
                        swal.fire({
                            title: 'Successful!',
                            text: 'Payment Gateways Updated successfully!',
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