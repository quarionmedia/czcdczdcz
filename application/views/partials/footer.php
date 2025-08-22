                <!-- JAVASCRIPT -->
                <script src="<?php echo base_url('assets/libs/jquery/jquery.min.js') ?>"></script>
                <script src="<?php echo base_url('assets/libs/bootstrap/js/bootstrap.bundle.min.js') ?>"></script>
                <script src="<?php echo base_url('assets/libs/metismenu/metisMenu.min.js') ?>"></script>
                <script src="<?php echo base_url('assets/libs/simplebar/simplebar.min.js') ?>"></script>
                <script src="<?php echo base_url('assets/libs/node-waves/waves.min.js') ?>"></script>


                <script src="<?php echo base_url('assets/libs/jquery-ui/ui/minified/widget.min.js') ?>"></script>
                <script src="<?php echo base_url('assets/libs/jquery-ui/ui/minified/tabs.min.js') ?>"></script>

         <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>   

        <!-- Peity chart-->
        <script src="<?php echo base_url('assets/libs/peity/jquery.peity.min.js') ?>"></script>

        <!-- Plugin Js-->
        <script src="<?php echo base_url('assets/libs/chartist/chartist.min.js') ?>"></script>
        <script src="<?php echo base_url('assets/libs/chartist-plugin-tooltips/chartist-plugin-tooltip.min.js') ?>"></script>
        <script src="<?php echo base_url('assets/libs/dropzone/min/dropzone.min.js')?>"></script>

        <script src="<?php echo base_url('assets/js/pages/dashboard.init.js') ?>"></script>

        <!-- Toaster js -->
        <script src="<?php echo base_url('assets/libs/toaster/toastr.min.js') ?>"></script>

        <!-- Sweet Alerts js -->
        <script src="<?php echo base_url('assets/libs/sweetalert2/sweetalert2.min.js') ?>"></script>

        <!-- Sweet alert init js-->
        <script src="<?php echo base_url('assets/js/pages/sweet-alerts.init.js') ?>"></script>

        <!-- QR Code-->
        <script src="<?php echo base_url('assets/libs/qrcode/qrcode_styling/qr-code-styling/lib/qr-code-styling.js') ?>"></script>

        <!-- Echarts-->
        <script src="<?php echo base_url('assets/libs/echarts/echarts.js') ?>"></script>

        <!-- Summernote js -->
        <script src="<?php echo base_url('assets/libs/summernote/summernote.js') ?>"></script>

        <!-- form repeater js -->
        <script src="<?php echo base_url('assets/libs/jquery.repeater/jquery.repeater.min.js') ?>"></script>

        <!--Tempus Dominus-->
        <script type="text/javascript" src="<?php echo base_url('assets/libs/Tempus Dominus/moment.min.js') ?>"></script>
        <script src="<?php echo base_url('assets/libs/Tempus Dominus/tempusdominus-bootstrap-4.min.js') ?>"></script>
        <link rel="stylesheet" href="<?php echo base_url('assets/libs/Tempus Dominus/tempusdominus-bootstrap-4.min.css') ?>" />

        <!-- Required datatable js -->
        <script src="<?php echo base_url('assets/libs/datatables.net/js/jquery.dataTables.min.js') ?>"></script>
        <script src="<?php echo base_url('assets/libs/datatables.net-bs4/js/dataTables.bootstrap4.min.js') ?>"></script>

        <script src="<?php echo base_url('assets/libs/select2/js/select2.min.js') ?>"></script>
        
        <script src="<?php echo base_url('assets/libs/bootstrap-datepicker/js/bootstrap-datepicker.min.js') ?>"></script>

        <script src="<?php echo base_url('assets/libs/spectrum-colorpicker2/spectrum.min.js') ?>"></script>

        <script src="<?php echo base_url('assets/js/app.js') ?>"></script>

        <!-- Custom js-->
        <script src="<?php echo base_url('assets/js/custom.js') ?>"></script>

        <script type="text/javascript">
            $('.table-responsive').on('show.bs.dropdown', function () {
                $('.table-responsive').css( "overflow", "inherit" );
            });

            $('.table-responsive').on('hide.bs.dropdown', function () {
                $('.table-responsive').css( "overflow", "auto" );
            })
            $('#QRCode-modal-content').css('background', 'white');

            const qrCode = new QRCodeStyling({
               width: 330,
               height: 330,
               data: btoa(JSON.stringify(<?php echo get_instance()->getQrData(); ?>)),
               margin: 0,
                qrOptions: {
                  typeNumber: "0",
                  mode: "Byte",
                  errorCorrectionLevel: "Q"
                },
                imageOptions: {
                  hideBackgroundDots: true,
                  imageSize: 0.4,
                  margin: 0
                },
              dotsOptions: {
                type: "extra-rounded",
                color: "#00000"
              },
              backgroundOptions: {
                color: "#ffffff",
                gradient: null
              },
              dotsOptionsHelper: {
                colorType: {
                  single: true,
                  gradient: false
                },
                gradient: {
                  linear: true,
                  radial: false,
                  color1: "#6a1a4c",
                  color2: "#6a1a4c",
                  rotation: "0"
                }
              },
              cornersSquareOptions: {
                type: "extra-rounded",
                color: "#000000"
              },
              cornersSquareOptionsHelper: {
                colorType: {
                  single: true,
                  gradient: false
                },
                gradient: {
                  linear: true,
                  radial: false,
                  color1: "#000000",
                  color2: "#000000",
                  rotation: "0"
                }
              },
              cornersDotOptions: {
                type: "dot"
              },
              cornersDotOptionsHelper: {
                colorType: {
                  single: true,
                  gradient: false
                },
                gradient: {
                  linear: true,
                  radial: false,
                  color1: "#000000",
                  color2: "#000000",
                  rotation: "0"
                }
              },
              backgroundOptionsHelper: {
                colorType: {
                  single: true,
                  gradient: false
                },
                gradient: {
                  linear: true,
                  radial: false,
                  color1: "#ffffff",
                  color2: "#ffffff",
                  rotation: "0"
                }
              }
            });
            
            qrCode.append(document.getElementById("qrcode"));
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

        <?php
        if(get_instance()->instantVerify()) {
            echo "<script type='text/javascript'>
                $(document).ready(function(){
                $('#LicenseModal').modal('show');
            }); </script>";
        }
        ?>
    </body>

</html>