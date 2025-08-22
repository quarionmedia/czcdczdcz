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

            						<h4 class="font-size-18">Subscription Plan</h4>

            						<ol class="breadcrumb mb-0">

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Dooo</a></li>

            							<li class="breadcrumb-item"><a href="javascript: void(0);">Subscription</a></li>

            							<li class="breadcrumb-item active">Subscription Plan</li>

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
                        							<h4 class="mb-3 mb-md-0">Subscription Plans</h4>
                        						</div>
                        						<div class="d-flex align-items-center flex-wrap text-nowrap">
                        							<div class="panel-heading">
                        								<button data-bs-toggle="modal" data-bs-target="#Create_Plan_Modal"
                        									id="Create_Plan"
                        									class="btn btn-sm btn-primary waves-effect waves-light"><span
                        										class="btn-label"><i class="fa fa-plus"></i></span>
                        									Create
                        									Plan</button>
                        							</div>
                        						</div>
                        					</div>

                        				</div>

                        				<br>

                        				<div class="panel-body">
                        					<div>
                        						<table id="datatable" class="table table-striped"
                        							style="border-collapse: collapse; border-spacing: 0; width: 100%;">

                        							<thead>

                        								<tr role="row">
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 9px;">#</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 50px;">Name</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 35px;">Time (Days)</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 57px;">Amount</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 56px;">Currency</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Background</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Subscription Type</th>
                                                            <th class="sorting_disabled" rowspan="1" colspan="1"
                                                                style="width: 57px;">Play Store Billing Product ID</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Status</th>
                        									<th class="sorting_disabled" rowspan="1" colspan="1"
                        										style="width: 68px;">Options</th>
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

            	</div>

                <!-- Create_Plan_Modal Modal -->
                <div class="modal fade" id="Create_Plan_Modal" tabindex="-1" role="dialog"
                    aria-labelledby="Create_Plan_Modal_Lebel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="Create_Plan_Modal_Lebel">Create Subscription plan</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                                </button>
                            </div>
                            <div class="modal-body">

                                <div class="panel-heading">
                                    <h3 class="panel-title row justify-content-center">Add Subscription Plan Details</h3>
                                </div>

                                <hr>

                                <div class="form-group mb-3"> <label class="control-label">Plan Name</label>&nbsp;&nbsp;<input id="modal_plan_name" type="text" name="label"
                                    class="form-control" placeholder="" required=""> </div>
                                <div class="form-group mb-3"> <label class="control-label">Time (Days)</label>&nbsp;&nbsp;<input id="modal_time" type="number" name="label"
                                    class="form-control" placeholder="" required=""> </div>
                                <div class="form-group mb-3"> <label class="control-label">Amount</label>&nbsp;&nbsp;<input id="modal_ammount" type="number" name="label"
                                    class="form-control" placeholder="" required=""> </div>

                                <div class="form-group mb-3"> <label class="control-label">Google Play Product ID (if you are using Google Play Billing): Optional</label>&nbsp;&nbsp;<input id="google_play_product_id" type="text" name="label"
                                                                                                                             class="form-control" placeholder="" required=""> </div>
                                    <div class="form-group mb-3">
                                        <label>Currency</label>
                                        <select class="form-control" id="modal_currency">
                                        <?php include("partials/source/currency_list.php"); ?>
                                        </select>
                                    </div>
                                <div class="form-group mb-3"> <label class="control-label">Background Image URL</label>&nbsp;&nbsp;<input id="modal_bg_image_url" type="text" name="label"
                                    class="form-control" placeholder="" required=""> </div>


                                    <div class="form-group mb-3">
                                    <label class="control-label">Subscription Type</label>
                                        <hr>

                                        <div class="form-group row mb-3">
                                            <label class="control-label col-sm-6 ">Remove Ads</label>
                                            <div class="col-sm-6">
                                                <input type="checkbox" id="Remove_Ads_bool" switch="bool">
                                                <label for="Remove_Ads_bool" data-on-label=""
                                                    data-off-label=""></label>
                                            </div>
                                        </div>
                                        <div class="form-group row mb-3">
                                            <label class="control-label col-sm-6 ">Watch Premium Contents</label>
                                            <div class="col-sm-6">
                                                <input type="checkbox" id="Watch_Premium_Contents_bool"
                                                    switch="bool">
                                                <label for="Watch_Premium_Contents_bool" data-on-label=""
                                                    data-off-label=""></label>
                                            </div>
                                        </div>
                                        <div class="form-group row mb-3">
                                            <label class="control-label col-sm-6 ">Download Premium Contents</label>
                                            <div class="col-sm-6">
                                                <input type="checkbox" id="Download_Premium_Contents_bool"
                                                    switch="bool">
                                                <label for="Download_Premium_Contents_bool" data-on-label=""
                                                    data-off-label=""></label>
                                            </div>
                                        </div>
                                        <hr>
                                        </div>





                                    <div class="form-group mb-3">
                                        <label>Publish</label>
                                        <div>
                                            <input type="checkbox" id="Publish_toggle" switch="bool" checked />
                                            <label for="Publish_toggle" data-on-label="" data-off-label=""></label>
                                        </div>
                                    </div>



                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="button" onclick="Create_Plan()" class="btn btn-primary"
                                        id="Fetch_btn">Create</button>
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
        "ajax": '<?= site_url('Admin_api/get_all_subscriptions') ?>',
        "columns": [{
                "data": "1",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
            {
                "data": "2"
            },
            {
                "data": "3",
                render: function (data) {
                    return data+" Days";
                }
            },
            {
                "data": "4"
            },
            {
                "data": "5",
                render: function (data) {
                    return getCurrencyLabel(data);
                }
            },
            {
                "data": "6",
                render: function (data) {
                    return '<img class="img-fluid" height="100" width="80" src='+ data +' data-holder-rendered="true">';
                }
            },
            {
                "data": "7",
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
                "data": "8",
            },
            {
                "data": "9",
                render: function (data) {
                    if (data == 0) {
                        return '<span class="badge bg-danger">UnPublished</span>';
                    } else if (data == 1) {
                        return '<span class="badge bg-success">Published</span>';
                    }
                }
            },
            {
                "data": "10",
                render: function (data) {
                    return '<div class="btn-group"> <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Options</button> <div class="dropdown-menu" style=""> <a class="dropdown-item" onclick="Delete_plan(' +
                                data + ')" href="#">Delete</a> </div> </div>';
                }
            }
        ]
    });

    function Create_Plan() {
        var modal_plan_name = document.getElementById("modal_plan_name").value;
        var modal_time = document.getElementById("modal_time").value;
        var modal_ammount = document.getElementById("modal_ammount").value;
        var google_play_product_id = document.getElementById("google_play_product_id").value;
        var modal_currency = document.getElementById("modal_currency").value;
        var modal_bg_image_url = document.getElementById("modal_bg_image_url").value;

        if ($('#Remove_Ads_bool').is(':checked')) {
            var Remove_Ads = 1;
        } else {
            var Remove_Ads = 0;
        }
        if ($('#Watch_Premium_Contents_bool').is(':checked')) {
            var Watch_Premium_Contents = 2;
        } else {
            var Watch_Premium_Contents = 0;
        }
        if ($('#Download_Premium_Contents_bool').is(':checked')) {
            var Download_Premium_Contents = 3;
        } else {
            var Download_Premium_Contents = 0;
        }

        var Subscription_Type = "";
        if (Remove_Ads != "0") {
            Subscription_Type = "" + Remove_Ads;
        }
        if (Watch_Premium_Contents != "0") {
            Subscription_Type = "" + Subscription_Type + Watch_Premium_Contents;
        }
        if (Download_Premium_Contents != "0") {
            Subscription_Type = "" + Subscription_Type + Download_Premium_Contents;
        }
        if (Subscription_Type != "") {
            var f_Subscription_Type = Subscription_Type;
        } else {
            var f_Subscription_Type = 0;
        }

        if ($('#Publish_toggle').is(':checked')) {
            var Publish_toggle_int = 1;
        } else {
            var Publish_toggle_int = 0;
        }
        
        var jsonObjects = {
            modal_plan_name: modal_plan_name,
            modal_time: modal_time,
            modal_ammount: modal_ammount,
            google_play_product_id: google_play_product_id,
            modal_currency: modal_currency,
            modal_bg_image_url: modal_bg_image_url,
            f_Subscription_Type: f_Subscription_Type,
            Publish_toggle_int: Publish_toggle_int
        };
        $.ajax({
            type: 'POST',
            url: '<?= site_url('Admin_api/create_sub_plan') ?>',
            data: jsonObjects,
            dataType: 'text',
            success: function (response) {
                if (response != "") {
                    location.reload();
                }
            }
        });
    }

    function Delete_plan(ID) {
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
                    subscriptionID: ID
                };
                $.ajax({
                    type: 'POST',
                    url: '<?= site_url('Admin_api/delete_sub_plan') ?>',
                    data: jsonObjects,
                    dataType: 'text',
                    success: function (response) {
                        if (response) {
                            swal.fire({
                                title: 'Successful!',
                                text: 'Subscription Plan Deleted Successfully!',
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
                });
            }
        });
    }

    function getCurrencyLabel(data) {
        if (data == 0) {
            return '<label>INR (₹)</label>';
        } else if (data == 1) {
            return '<label>USD ($)</label>';
        } else if (data == 2) {
            return '<label>EUR (€)</label>';
        } else if (data == 3) {
            return '<label>GBP (£)</label>';
        } else if (data == 4) {
            return '<label>JPY (¥)</label>';
        } else if (data == 5) {
            return '<label>CHF (CHF)</label>';
        } else if (data == 6) {
            return '<label>CAD (CA$)</label>';
        } else if (data == 7) {
            return '<label>AUD (AU$)</label>';
        } else if (data == 8) {
            return '<label>NZD (NZ$)</label>';
        } else if (data == 9) {
            return '<label>CNY (CN¥)</label>';
        } else if (data == 10) {
            return '<label>BRL (R$)</label>';
        } else if (data == 11) {
            return '<label>RUB (₽)</label>';
        } else if (data == 12) {
            return '<label>ZAR (R)</label>';
        } else if (data == 13) {
            return '<label>MXN (MX$)</label>';
        } else if (data == 14) {
            return '<label>SGD (S$)</label>';
        } else if (data == 15) {
            return '<label>HKD (HK$)</label>';
        } else if (data == 16) {
            return '<label>NOK (kr)</label>';
        } else if (data == 17) {
            return '<label>SEK (kr)</label>';
        } else if (data == 18) {
            return '<label>DKK (kr)</label>';
        } else if (data == 19) {
            return '<label>TRY (₺)</label>';
        } else if (data == 20) {
            return '<label>AED (AED)</label>';
        } else if (data == 21) {
            return '<label>SAR (﷼)</label>';
        } else if (data == 22) {
            return '<label>KRW (₩)</label>';
        } else if (data == 23) {
            return '<label>IDR (Rp)</label>';
        } else if (data == 24) {
            return '<label>MYR (RM)</label>';
        } else if (data == 25) {
            return '<label>THB (฿)</label>';
        } else if (data == 26) {
            return '<label>PLN (zł)</label>';
        } else if (data == 27) {
            return '<label>HUF (Ft)</label>';
        } else if (data == 28) {
            return '<label>CZK (Kč)</label>';
        } else if (data == 29) {
            return '<label>ISK (kr)</label>';
        } else if (data == 30) {
            return '<label>CLP ($)</label>';
        } else if (data == 31) {
            return '<label>COP ($)</label>';
        } else if (data == 32) {
            return '<label>ARS ($)</label>';
        } else if (data == 33) {
            return '<label>PEN (S/.)</label>';
        } else if (data == 34) {
            return '<label>UAH (₴)</label>';
        } else if (data == 35) {
            return '<label>QAR (﷼)</label>';
        } else if (data == 36) {
            return '<label>BHD (BD)</label>';
        } else if (data == 37) {
            return '<label>OMR (﷼)</label>';
        } else if (data == 38) {
            return '<label>KWD (د.ك)</label>';
        } else if (data == 39) {
            return '<label>DZD (دج)</label>';
        } else if (data == 40) {
            return '<label>BBD ($)</label>';
        } else if (data == 41) {
            return '<label>BZD (BZ$)</label>';
        } else if (data == 42) {
            return '<label>BMD ($)</label>';
        } else if (data == 43) {
            return '<label>BTN (Nu.)</label>';
        } else if (data == 44) {
            return '<label>BOB (Bs.)</label>';
        } else if (data == 45) {
            return '<label>BAM (KM)</label>';
        } else if (data == 46) {
            return '<label>BWP (P)</label>';
        } else if (data == 47) {
            return '<label>BND ($)</label>';
        } else if (data == 48) {
            return '<label>BGN (лв)</label>';
        } else if (data == 49) {
            return '<label>MMK (Ks)</label>';
        } else if (data == 50) {
            return '<label>BIF (FBu)</label>';
        } else if (data == 51) {
            return '<label>KHR (៛)</label>';
        } else if (data == 52) {
            return '<label>XAF (FCFA)</label>';
        } else if (data == 53) {
            return '<label>XOF (CFA)</label>';
        } else if (data == 54) {
            return '<label>CVE (CVE)</label>';
        } else if (data == 55) {
            return '<label>KYD ($)</label>';
        } else if (data == 56) {
            return '<label>XPF (₣)</label>';
        } else if (data == 57) {
            return '<label>CLF (UF)</label>';
        } else if (data == 58) {
            return '<label>CUP ($)</label>';
        } else if (data == 59) {
            return '<label>CUC ($)</label>';
        } else if (data == 60) {
            return '<label>DJF (Fdj)</label>';
        } else if (data == 61) {
            return '<label>DOP (RD$)</label>';
        } else if (data == 62) {
            return '<label>EGP (£)</label>';
        } else if (data == 63) {
            return '<label>ERN (Nfk)</label>';
        } else if (data == 64) {
            return '<label>ETB (Br)</label>';
        } else if (data == 65) {
            return '<label>FKP (£)</label>';
        } else if (data == 66) {
            return '<label>FJD ($)</label>';
        } else if (data == 67) {
            return '<label>GMD (D)</label>';
        } else if (data == 68) {
            return '<label>GEL (₾)</label>';
        } else if (data == 69) {
            return '<label>GHS (GH₵)</label>';
        } else if (data == 70) {
            return '<label>GIP (£)</label>';
        } else if (data == 71) {
            return '<label>GTQ (Q)</label>';
        } else if (data == 72) {
            return '<label>GNF (FG)</label>';
        } else if (data == 73) {
            return '<label>GYD ($)</label>';
        } else if (data == 74) {
            return '<label>HTG (G)</label>';
        } else if (data == 75) {
            return '<label>HNL (L)</label>';
        } else if (data == 76) {
            return '<label>HRK (kn)</label>';
        } else if (data == 77) {
            return '<label>HTG (G)</label>';
        } else if (data == 78) {
            return '<label>HNL (L)</label>';
        } else if (data == 79) {
            return '<label>HRK (kn)</label>';
        } else if (data == 80) {
            return '<label>HTG (G)</label>';
        } else if (data == 81) {
            return '<label>HNL (L)</label>';
        } else if (data == 82) {
            return '<label>HRK (kn)</label>';
        } else if (data == 83) {
            return '<label>HUF (Ft)</label>';
        } else if (data == 84) {
            return '<label>IDR (Rp)</label>';
        } else if (data == 85) {
            return '<label>ILS (₪)</label>';
        } else if (data == 86) {
            return '<label>IMP (£)</label>';
        } else if (data == 87) {
            return '<label>IRR (﷼)</label>';
        } else if (data == 88) {
            return '<label>IQD (ع.د)</label>';
        } else if (data == 89) {
            return '<label>IRT (₹)</label>';
        } else if (data == 90) {
            return '<label>BDT (৳)</label>';
        }
    }

    </script>