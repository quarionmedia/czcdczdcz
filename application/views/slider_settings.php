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

                        			<h4 class="font-size-18">Slider Settings</h4>

                        			<ol class="breadcrumb mb-0">

                        				<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

                        				<li class="breadcrumb-item"><a href="javascript: void(0);">Slider</a></li>

                        				<li class="breadcrumb-item active">Slider Settings</li>

                        			</ol>

                        		</div>

                        	</div>

                        </div>

                        <!-- end page title -->

                        <div class="row">

                        	<div class="col-12">

                        		<div class="card">

                        			<div class="card-body">

                        				<h3 class="card-title mt-0">Slider Settings</h3>
                        				<hr>

                        				<div class="col-md-8">
                        					<label class="control-label">Slider Type</label>
                        					<select class="form-control m-bot15 col-4" id="slider_type"
                        						name="slider_type">
                        						<option value="0" selected="" id="ad1_image_selection">Movie Slider
                        						</option>
                        						<option value="1" id="ad1_code_selection">Web Series Slider</option>
                        						<option value="2" id="ad1_code_selection">Custom Slider</option>
                        						<option value="3" id="ad1_disable">Disable</option>
                        					</select>
                        				</div>

                        				<pre> </pre>

                        				<div class="col-md-8">
                        					<label class="control-label">Movie Image Slider Max Visible</label>

                        					<input class="form-control" type="number"
                        						id="movie_image_slider_max_visible" value="<?php echo $data->movie_image_slider_max_visible; ?>">





                        					<pre> </pre>



                        					<label class="control-label">Web Series Image Slider Max
                        						Visible</label>


                        					<input class="form-control col-5" type="number"
                        						id="webseries_image_slider_max_visible" value="<?php echo $data->webseries_image_slider_max_visible; ?>">

                        				</div>
                                        <br>
                        				<div class="form-group">
                        					<div class="col-md-12 row justify-content-end">
                        						<button class="btn btn-primary dropdown-toggle waves-effect waves-light col-md-2"
                        							onclick="Save_Slider_Config()" id="create_btn" type="submit"
                        							aria-haspopup="true" aria-expanded="false">
                        							<i class="mdi mdi-content-save-all"></i> SAVE
                        						</button>
                        					</div>
                        				</div>
                                        <br>
                        			</div>
                        		</div>
                        	</div>
                        </div>


                    </div> <!-- container-fluid -->
                </div>
                <!-- End Page-content -->


                <?php include("partials/footer_rights.php"); ?>
                

            </div>
            <!-- end main content-->

        </div>
        <!-- END layout-wrapper -->

        <?php include("partials/footer.php"); ?>

        <script>
            $("#slider_type").val(<?php echo $data->image_slider_type; ?>);


            function Save_Slider_Config() {
                var Slider_Type = document.getElementById("slider_type").value;
                var movie_image_slider_max_visible = document.getElementById("movie_image_slider_max_visible").value;
                var webseries_image_slider_max_visible = document.getElementById("webseries_image_slider_max_visible").value;


                $.ajax({
                  url: '<?= site_url('Admin_api/updateSliderConfig') ?>',
                  type: 'POST',
                  data : { Slider_Type : Slider_Type, movie_image_slider_max_visible: movie_image_slider_max_visible, webseries_image_slider_max_visible:webseries_image_slider_max_visible },
                  dataType:'text',
                    success: function(result){
                        if(result) {
                            swal.fire({
                                title: 'Successful!',
                                text: 'Slider Config Updated successfully!',
                                icon: 'success',
                                showCancelButton: false,
                                confirmButtonColor: '#556ee6',
                                cancelButtonColor: "#f46a6a"
                            }).then(function () {
                                location.reload();
                            });
                        }
                    }
                });
            }
            
        </script>