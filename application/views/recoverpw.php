<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!doctype html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <title>Login | Dooo - Movie & Web Series Portal App</title>
        
        <?php include("partials/header.php"); ?>
    </head>

<body>

    <div class="home-btn d-none d-sm-block">
        <a href="" class="text-dark"><i class="fas fa-home h2"></i></a>
    </div>
    <div class="account-pages my-5 pt-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6 col-xl-4">
                    <div class="card overflow-hidden">
                        <div class="bg-primary">
                            <div class="text-primary text-center p-4">
                                <h5 class="text-white font-size-20">Forget Your Password?</h5>
                                <p class="text-white-50">Enter Your Mail to reset your Password</p>
                                <a href="" class="logo logo-admin">
                                    <img src="assets/images/logo-sm.png" height="24" alt="logo">
                                </a>
                            </div>
                        </div>

                        <div class="card-body p-4">

                            <div class="p-3">

                                <div class="alert alert-success mt-5" role="alert">
                                    Enter your Email and instructions will be sent to you!
                                </div>

                                <div class="form-group mb-3">
                                    <label for="useremail">Email</label>
                                    <input type="email" class="form-control" id="useremail" placeholder="Enter email">
                                </div>

                                <div class="form-group row mb-0">
                                    <div class="col-12 text-end">
                                        <button class="btn btn-primary w-md waves-effect waves-light" id="Reset_btn"
                                            onclick="Reset()">Send</button>
                                    </div>
                                </div>

                                <div class="form-group mb-3">
                                    <label for="useremail">Code</label>
                                    <input type="text" class="form-control" id="code" placeholder="Enter Code">
                                </div>

                                <div class="form-group row mb-0">
                                    <div class="col-12 text-end">
                                        <button class="btn btn-primary w-md waves-effect waves-light" id="Check_btn"
                                            onclick="checkCode()">Reset</button>
                                    </div>
                                </div>

                                <br>

                                <div class="row justify-content-center">
                                    <div class="col-lg-12">
                                        <div id="result" class="m-t-15"></div>
                                    </div>

                                </div>

                            </div>
                        </div>

                    </div>

                    <div class="mt-5 text-center">
                        <p class="mb-0">© <script>document.write(new Date().getFullYear())</script> Dooo. Crafted with <i class="mdi mdi-heart text-danger"></i> by Team Dooo</p>
                    </div>


                </div>
            </div>
        </div>
    </div>

                    <!-- JAVASCRIPT -->
                    <script src="assets/libs/jquery/jquery.min.js"></script>
                <script src="assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
                <script src="assets/libs/metismenu/metisMenu.min.js"></script>
                <script src="assets/libs/simplebar/simplebar.min.js"></script>
                <script src="assets/libs/node-waves/waves.min.js"></script>


        <!-- Peity chart-->
        <script src="assets/libs/peity/jquery.peity.min.js"></script>

        <!-- Plugin Js-->
        <script src="assets/libs/chartist/chartist.min.js"></script>
        <script src="assets/libs/chartist-plugin-tooltips/chartist-plugin-tooltip.min.js"></script>

        <script src="assets/js/pages/dashboard.init.js"></script>

        <!-- Sweet Alerts js -->
        <script src="assets/libs/sweetalert2/sweetalert2.min.js"></script>

        <!-- Sweet alert init js-->
        <script src="assets/js/pages/sweet-alerts.init.js"></script>

        <!-- QR Code-->
        <script src="assets/libs/qrcode/jquery.qrcode.min.js"></script>

        <script src="assets/js/app.js"></script>

    </body>

</html>

<script>
        function Reset() {
            var mail = document.getElementById("useremail").value;

            $.ajax({
                type: 'POST',
                url: '<?= site_url('Password_reset/password_reset_mail') ?>',
                data: {
                    'mail': mail
                },
                dataType: 'text',
                beforeSend: function () {
                    $("#Reset_btn").html('Sending...');
                },
                success: function (response) {
                    $("#Reset_btn").html('Send');
                    if (response == "") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Something Went Wrong!</div>'
                        );
                    } else if (response == "Email Not Registered") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Email Not Registered!</div>'
                        );
                    } else if (response == "Something Went Wrong!") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Something Went Wrong!</div>'
                        );
                    } else {
                        $('#result').html(
                            '<div class="alert alert-success alert-dismissable show"><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>instructions Sended To the Mail Address!</div>'
                        );
                    }
                }
            });
        }

        function checkCode() {
            var code = document.getElementById("code").value;
            $.ajax({
                type: 'POST',
                url: '<?= site_url('Password_reset/checkCode') ?>',
                data: {
                    'code': code
                },
                dataType: 'text',
                beforeSend: function () {
                    $("#Check_btn").html('Processing...');
                },
                success: function (response) {
                    $("#Check_btn").html('Reset');
                    if (response == "") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Something Went Wrong!</div>'
                        );
                    } else if (response == "Invalid Request") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Invalid Code!</div>'
                        );
                    } else if (response == "Expired") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Code Expired!</div>'
                        );
                    } else if (response == "Used Code") {
                        $('#result').html(
                            '<div class="alert alert-danger alert-dismissable show><button type="button" class="btn-close" data-bs-dismiss="alert" aria-hidden="true"></button>Code was already Used!</div>'
                        );
                    } else {
                        window.location.assign("changepass");
                    }
                }
            });
        }
    </script>

<script type="module">
          import devtools from "<?php echo base_url('/assets/js/devtools.js'); ?>";
          $(document).ready(function() {
              if (devtools.isOpen) {
                  var all = document.querySelectorAll("*");
              
                  for (var each of all) {
                      each.classList.add(`asdjaljsdliasud8ausdijaisdluasdjasildahjdsk${Math.random()}`);
                  }
              }
          });
          window.addEventListener('devtoolschange', event => {
            var all = document.querySelectorAll("*");
              
                  for (var each of all) {
                      each.classList.add(`asdjaljsdliasud8ausdijaisdluasdjasildahjdsk${Math.random()}`);
                  }
	        });
          
        </script>