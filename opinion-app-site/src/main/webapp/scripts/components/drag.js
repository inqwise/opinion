<style type="text/css">
          
          ol.sort {
            list-style: none;
          }
          
          ol.sort li {
            margin-top: 1px;
            margin-bottom: 1px;
            border: transparent thin solid;
            width: 10em;
          }
          
          ol.sort li.drophover {
            border-color: gray;
          }
          
          ol.sort li:focus,
          ol.sort li:active {
            background-color: #EEEEEE;
          }

          ol.sort li.key-drag {
            color: #333333;
            border-color: black;
          }

        </style>

<ol class="sort" role="listbox" aria-labelledby="q1">
        <li role="listitem" tabindex="0"  id="q1-6">Revolutionary war</li>
        <li role="listitem" tabindex="-1" id="q1-4">Mexican American War</li>
        <li role="listitem" tabindex="-1" id="q1-2">World War 2</li>

        <li role="listitem" tabindex="-1" id="q1-1">War of 1812</li>
        <li role="listitem" tabindex="-1" id="q1-5">Spanish American War</li>
        <li role="listitem" tabindex="-1" id="q1-3">World War 1</li>
      </ol> 
      <div id="pointer" style="position: absolute; top: -30em; left: -30em">>></div>


$(document).ready(function(){
            
              var KEY_PAGEUP   = 33;
              var KEY_PAGEDOWN = 34;
              var KEY_END      = 35;
              var KEY_HOME     = 36;
            
              var KEY_LEFT     = 37;
              var KEY_UP       = 38;
              var KEY_RIGHT    = 39;
              var KEY_DOWN     = 40;
            
              var KEY_SPACE    = 32;
              var KEY_TAB      = 9;
            
              var KEY_BACKSPACE = 8;
              var KEY_DELETE    = 46;
              var KEY_ENTER     = 13;
              var KEY_INSERT    = 45;
              var KEY_ESCAPE    = 27;
              
              var KEY_M = 77;
            
              $("ol.sort li").draggable({
              
                axis: 'y',
                
                containment: 'parent',
                
                opacity: 0.7, 
                
                helper: 'clone',
              
                start: function( event, ui ) {
                
                  $(event.target).css('color', 'gray');
                
                }, // end start method
                
                stop: function( event, ui ) {
                
                  $(event.target).css('color', 'black');
                
                } // end stop method
                
              
              });
              
                                          
              $("ol.sort li").mousedown( 
                 function( event ) { 

                   var start_top = $(event.target).offset().top; 
                   
                   $(event.target).focus();

                   // enable dropping on only this group of questions            

                   $('ol.sort li').droppable('destroy');

                   $(event.target).parent().children('li').droppable({
                   
                     hoverClass: 'drophover',
                   
                     drop: function(event, ui) {
              
                       if( parseInt($(event.target).offset().top) > parseInt(start_top) ) {
                       
                          $(event.target).after( $(ui.draggable) )
                         
                       } else {

                           $(event.target).before( $(ui.draggable) )
                         
                       }  // endif

                       $(ui.draggable).css('top', '');
                       $(ui.draggable).css('left', '');
                       $(ui.draggable).focus();
                       $(event.target).parent().children('li').droppable('destroy');

                     }, // end drop method
                     
                     deactivate: function(event, ui) {
                       $(ui.draggable).css('top', '');
                       $(ui.draggable).css('left', '');
                     } // end deactivate method
                     
                    }  // end droppable properties
                   ); // end droppable event
             
                }  // end mousedown function
              ); // end mousedown event

              var drag_state = false;
              var drag_top;
              var drag_node;
              
              $("ol.sort li").attr('aria-grabbed', 'false');

              $("ol.sort li").keydown( 
               function( event ) { 
               
                  var next_item;

                 
                  switch( event.keyCode ) {
                  
                    case KEY_UP:
                    case KEY_LEFT:
                    
                      if( !event.altKey && !event.shiftKey ) {
                        
                        next_item = $(event.target).prev();
                        
                        if( !event.ctrlKey ) {
                      
                          if( next_item.length ) {
                            $(event.target).attr('tabindex', -1 );
                            $(next_item).attr('tabindex', 0 );
                            $(next_item).focus();
                          
                            if( drag_state ) {
                              $("div#pointer").css('top', $(next_item).offset().top); 
                              $("div#pointer").css('left', $(next_item).offset().left-20); 
                            }  // endif
                          
                          } // endif
                          
                        } else {
                          
                          if( next_item.length ) {
                            $(next_item).before( $(event.target) )
                            $(event.target).focus();
                          } // endif
                        
                        } // endif
                        
                        event.stopPropagation();
                        event.preventDefault();
                        return false;
                      }
                      break;

                    case KEY_DOWN:
                    case KEY_RIGHT:

                      if( !event.altKey && !event.shiftKey ) {

                         next_item = $(event.target).next();

                         if( !event.ctrlKey ) {

                           if( next_item.length ) {
                             $(event.target).attr('tabindex', -1 );
                             $(next_item).attr('tabindex', 0 );
                             $(next_item).focus();
                          
                             if( drag_state ) {
                               $("div#pointer").css('top', $(next_item).offset().top); 
                               $("div#pointer").css('left', $(next_item).offset().left-20); 
                             }  // endif
                          
                           }
                           
                        } else {
                        
                          if( next_item.length ) {
                            $(next_item).after( $(event.target) )
                            $(event.target).focus();
                          }  // endif
                        
                        } // endif 
                        
                        event.stopPropagation();
                        event.preventDefault();
                        return false;
                        
                      } // endif  
                     break;
                     
                     case KEY_M:
                     
                       if( event.ctrlKey && !event.altKey  ) {

                         if( drag_state ) {
                           
                           if( event.target != drag_node ) {
                             if(  $(event.target).offset().top > drag_top ) {
                               $(event.target).after( $(drag_node) )
                             } else {
                               $(event.target).before( $(drag_node) )
                             } // endif
                           } // endif  
                           
                           drag_state = false;
                           $(event.target).attr('aria-grabbed', 'false');
                           $(event.target).siblings('li').attr('aria-grabbed', 'false');
                           $(event.target).siblings('li').removeAttr('aria-dropeffect');
                           $(drag_node).focus();
                           $(drag_node).removeClass('key-drag');
                           $("div#pointer").css('top', -300); 
                           $("div#pointer").css('left', -300); 
                         } else {
                           $(event.target).attr('aria-grabbed', 'true');
                           $(event.target).siblings('li').attr('aria-dropeffect', 'move');
                           drag_state = true;
                           drag_top = $(event.target).offset().top;
                           drag_node = event.target;
                           $(drag_node).addClass('key-drag');
                           $("div#pointer").css('top', $(drag_node).offset().top); 
                           $("div#pointer").css('left', $(drag_node).offset().left-20); 
                         } // endif
                     
                         event.stopPropagation();
                         event.preventDefault();
                         return false;
                         
                       } // endif
                       break;
                       
                       case KEY_ESCAPE:
                         if( drag_state ) {
                           drag_state = false;
                           $(event.target).attr('aria-grabbed', 'false');
                           $(event.target).siblings('li').attr('aria-grabbed', 'false');
                           $(event.target).siblings('li').removeAttr('aria-dropeffect');
                           $(drag_node).focus();
                           $(drag_node).removeClass('key-drag');
                           $("div#pointer").css('top', -300); 
                           $("div#pointer").css('left', -300); 
                           event.stopPropagation();
                           event.preventDefault();
                           return false;
                         } // endif
                         break;
                         
                       case KEY_TAB:
                         if( drag_state ) {
                           drag_state = false;
                           $(event.target).attr('aria-grabbed', 'false');
                           $(event.target).siblings('li').attr('aria-grabbed', 'false');
                           $(event.target).siblings('li').removeAttr('aria-dropeffect');
                           $("div#pointer").css('top', -300); 
                           $("div#pointer").css('left', -300); 
                           $(drag_node).removeClass('key-drag');
                         } // endif
                         
                        break;

                  } // end switch
                  
                  return true;

               }  // end keydown function
              ); // end keydown event


             }  // end ready function
            );  // ready event
            
