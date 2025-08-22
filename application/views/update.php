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

            						<h4 class="font-size-18">Update</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">System</a></li>

            							<li class="breadcrumb-item active">Update</li>

            						</ol>

            					</div>

            				</div>

            			</div>

            			<!-- end page title -->

                        <div class="row">

                        	<div class="col-md-12">

                        		<div class="card card-body">

                                    <h4 class="card-title">Update</h4>
                                    <p class="card-title-desc">System Update info</p>

                                    <!-- Nav tabs -->
                        			<ul class="nav nav-tabs nav-tabs-custom" role="tablist">
                        				<li class="nav-item">
                        					<a class="nav-link active" data-bs-toggle="tab" href="#Automatic" role="tab">
                        						<span class="d-none d-md-block">Automatic</span><span
                        							class="d-block d-md-none"><i class="mdi mdi-home-variant h5"></i></span>
                        					</a>
                        				</li>
                        				<li class="nav-item">
                        					<a class="nav-link" data-bs-toggle="tab" href="#Manual" role="tab">
                        						<span class="d-none d-md-block">Manual</span><span class="d-block d-md-none"><i
                        								class="mdi mdi-account h5"></i></span>
                        					</a>
                        				</li>
                        			</ul>

                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div class="tab-pane active p-3" id="Automatic" role="tabpanel">
                                            <div class="form-group row text-center mb-3">
                                            	<label class="control-label col-sm-3">Dashboard Version</label>
                                            	<div class="col-sm-3" id="Dashboard_Version">
                                                    <span class="badge bg-warning"><?php echo $dashboardVersion?></span>
                                            	</div>

                                                <label class="control-label col-sm-3">Latest Version</label>
                                            	<div class="col-sm-3" id="Latest_Version">
                                                    <span class="badge bg-info"><?php echo $latestVersion ?></span>
                                            	</div>
                                            </div>

                                            <?php if(!$isUpdateAvailable) { ?>
                                                <div class="py-4 text-center mb-3">
                                                	<i
                                                		class="ion ion-md-checkmark-circle-outline display-4 text-success"></i>

                                                	<h5 class="text-dark mt-4">Your System is up to date.</h5>
                                                	<p class="text-muted">version <?php echo $dashboardVersion ?></p>
                                                	<div class="mt-4">
                                                		<a href="" class="btn btn-primary btn-sm">
                                                			<i class="ion ion-md-refresh"></i> Chack Status</a>
                                                	</div>
                                                </div>
                                            <?php } else { ?>
                                                <div class="py-4 text-center mb-3">
                                                	<i class="ion ion-md-cloud-download display-4 text-primary"></i>

                                                	<h5 class="text-dark mt-4">Time To Update.</h5>
                                                	<p class="text-muted">This Version of Dooo is out of date.</p>
                                                	<div class="mt-4">
                                                		<a href="" class="btn btn-primary btn-sm">
                                                			<i class="ion ion-md-refresh"></i> Chack Status</a>
                                                	</div>
                                                </div>

                                                <div class="form-group mb-3">
                                            	<div class="col-md-12 row justify-content-end">
                                            		<button
                                            			class="btn btn-primary dropdown-toggle waves-effect waves-light col-md-2"
                                            			id="create_btn" type="submit" aria-haspopup="true"
                                            			aria-expanded="false" onclick="update()">
                                            			<i class="ion ion-md-git-compare active"></i> Update
                                            		</button>
                                            	</div>
                                            </div>
                                            <?php } ?>
                                        
                                        </div>
                                        <div class="tab-pane p-3" id="Manual" role="tabpanel">
										    <div class="alert alert-info bg-info text-white border-0" role="alert">
                                                <strong>Upcomming!</strong> This Part of The Panel is Under Devlopment.
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

	function update() {
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

		$.ajax({
            type: 'POST',
            url: '<?= site_url('Update/update') ?>',
            dataType: 'text',
            success: function (response) {
				Swal.close();
                if (response == "Updated Successfully") {
                    swal.fire({
                        title: 'Successful!',
                        text: 'Dashboard Updated Successfully!',
                        icon: 'success',
                        showCancelButton: false,
                        confirmButtonColor: '#556ee6',
                        cancelButtonColor: "#f46a6a"
                    }).then(function () {
                        location.reload();
                    });
                } else if(response == 'Invalid File') {
					swal.fire({
                        title: 'Error',
                        text: "Invalid File!",
                        icon: 'error'
                    }).then(function () {
                        location.reload();
                    });
				}
            }
        });
		
	}

</script>