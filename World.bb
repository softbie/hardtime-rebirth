;//////////////////////////////////////////////////////////////////////////////
;------------------------------ HARD TIME: WORLD ------------------------------
;//////////////////////////////////////////////////////////////////////////////

;SET COLLISIONS
Function SetCollisions()
 ;entity physics
 For count=10 To 11
  Collisions 1,count,2,3 ;humans >>> scenery
  Collisions 2,count,2,3 ;shadows >>> scenery
  Collisions 3,count,2,1 ;weapon models >>> scenery
  Collisions 4,count,2,1 ;weapon pivots >>> scenery
 Next
 ;camera physics
 Collisions 5,10,2,3 ;camera >>> scenery
End Function

;--------------------------------------------------------------------
;///////////////////////// LOAD TERRAIN /////////////////////////////
;--------------------------------------------------------------------
Function LoadWorld()
 ;prison block
 If GetBlock(gamLocation(slot))>0
  world=LoadAnimMesh("World/Block/Block.3ds")
  EntityType world,10,1 
  Animate world,3,10
  cellLocked(gamLocation(slot),0)=0
  For count=1 To 20
   If gamLocation(slot)=1 Then EntityTexture FindChild(world,"Plate"+Dig$(count,10)),tBlock(1),0,2
   If gamLocation(slot)=3 Then EntityTexture FindChild(world,"Plate"+Dig$(count,10)),tBlock(2),0,2
   If gamLocation(slot)=5 Then EntityTexture FindChild(world,"Plate"+Dig$(count,10)),tBlock(3),0,2
   If gamLocation(slot)=7 Then EntityTexture FindChild(world,"Plate"+Dig$(count,10)),tBlock(4),0,2 
   EntityTexture FindChild(world,"Plate"+Dig$(count,10)),tCell(count),0,3
   EntityType FindChild(world,"Door"+Dig$(count,10)),11,0
   EntityType FindChild(world,"Plate"+Dig$(count,10)),11,0
   EntityType FindChild(world,"Bars"+Dig$(count,10)),11,0
   cellLocked(gamLocation(slot),count)=0
  Next
  EntityTexture FindChild(world,"Sign01"),tSign(9),0,2
  EntityTexture FindChild(world,"Sign02"),tSign(gamLocation(slot)),0,2
  no_chairs=0 : no_beds=20 : no_doors=1
  sAtmos=LoadSound("Sound/Ambience/Quiet.wav")
 EndIf
 ;exercise yard
 If gamLocation(slot)=2
  world=LoadAnimMesh("World/Yard/Yard.3ds")
  EntityType world,10,1
  EntityTexture FindChild(world,"Sign01"),tSign(9),0,2
  EntityTexture FindChild(world,"Net"),tNet
  EntityType FindChild(world,"Net"),0
  EntityType FindChild(world,"Rim"),0
  For count=1 To 2
   EntityTexture FindChild(world,"Fence0"+count),tFence
  Next
  no_chairs=6 : no_beds=0 : no_doors=1
  sAtmos=LoadSound("Sound/Ambience/Yard.wav")
 EndIf
 ;study
 If gamLocation(slot)=4
  world=LoadAnimMesh("World/Study/Study.3ds")
  EntityType world,10,1
  EntityTexture FindChild(world,"Sign01"),tSign(9),0,2
  EntityTexture FindChild(world,"Sign02"),tSign(10),0,2
  EntityFX FindChild(world,"Screen"),9
  no_chairs=5 : no_beds=0 : no_doors=2
  sAtmos=LoadSound("Sound/Ambience/Quiet.wav")
 EndIf
 ;hospital
 If gamLocation(slot)=6
  world=LoadAnimMesh("World/Hospital/Hospital.3ds")
  EntityType world,10,1
  EntityTexture FindChild(world,"Sign01"),tSign(9),0,2
  EntityTexture FindChild(world,"Sign02"),tSign(11),0,2
  EntityFX FindChild(world,"Screen"),9
  For count=1 To 4
   EntityAlpha FindChild(world,"Beaker0"+count),0.7
   EntityAlpha FindChild(world,"Water0"+count),0.5
  Next
  no_chairs=9 : no_beds=3 : no_doors=2
  sAtmos=LoadSound("Sound/Ambience/Hall.wav")
 EndIf
 ;kitchen
 If gamLocation(slot)=8
  world=LoadAnimMesh("World/Kitchen/Kitchen.3ds")
  EntityType world,10,1
  EntityTexture FindChild(world,"Sign01"),tSign(9),0,2
  For count=1 To 4
   EntityAlpha FindChild(world,"Window0"+count),0.5
  Next
  For tray=1 To 50
   trayOldState(tray)=-1
  Next
  no_chairs=45 : no_beds=0 : no_doors=1
  sAtmos=LoadSound("Sound/Ambience/Canteen.wav")
 EndIf
 ;main hall
 If gamLocation(slot)=9
  world=LoadAnimMesh("World/Hall/Hall.3ds")
  EntityType world,10,1
  For count=1 To 8
   EntityTexture FindChild(world,"Sign"+Dig$(count,10)),tSign(count),0,2
  Next
  For count=1 To 9 
   EntityShininess FindChild(world,"Ball"+Dig$(count,10)),1.0
  Next
  EntityFX FindChild(world,"TV"),9
  EntityFX FindChild(world,"Screen"),9
  For count=1 To 4
   EntityAlpha FindChild(world,"Alarm"+Dig$(count,10)),0.8
   EntityShininess FindChild(world,"Alarm"+Dig$(count,10)),1.0
   EntityColor FindChild(world,"Alarm"+Dig$(count,10)),5,0,0
   EntityShininess FindChild(world,"Phone"+Dig$(count,10)),1.0
   phoneX#(count)=EntityX(FindChild(world,"Phone"+Dig$(count,10)),1)
   phoneY#(count)=EntityY(FindChild(world,"Phone"+Dig$(count,10)),1)
   phoneZ#(count)=EntityZ(FindChild(world,"Phone"+Dig$(count,10)),1)
  Next
  no_chairs=8 : no_beds=0 : no_doors=8
  sAtmos=LoadSound("Sound/Ambience/Hall.wav")
 EndIf
 ;workshop
 If gamLocation(slot)=10
  world=LoadAnimMesh("World/Workshop/Workshop.3ds")
  EntityType world,10,1
  EntityTexture FindChild(world,"Sign01"),tSign(4),0,2
  no_chairs=6 : no_beds=0 : no_doors=1
  For cyc=1 To 6
   kit(cyc)=LoadAnimMesh("Weapons/"+weapFile$(kitType(cyc))+" Kit.3ds")
   ScaleEntity kit(cyc),0.4,0.4,0.4
   limb=FindChild(world,"Table"+Dig$(cyc,10))
   PositionEntity kit(cyc),EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
   If cyc=>5 Then RotateEntity kit(cyc),0,90,0
   If weapTex(kitType(cyc))>0
    For count=1 To CountChildren(kit(cyc))
     EntityTexture GetChild(kit(cyc),count),weapTex(kitType(cyc))
    Next
   EndIf
   If kitState(cyc)=0 Then HideEntity kit(cyc)
  Next
  sAtmos=LoadSound("Sound/Ambience/Quiet.wav")
 EndIf
 ;toilets
 If gamLocation(slot)=11
  world=LoadAnimMesh("World/Toilets/Toilets.3ds")
  EntityType world,10,1
  EntityTexture FindChild(world,"Sign01"),tSign(6),0,2
  For count=1 To 5 
   EntityType FindChild(world,"Door"+Dig$(count,10)),11,0
   EntityAlpha FindChild(world,"Water"+Dig$(count,10)),0.5
  Next
  For count=1 To 4
   EntityType FindChild(world,"Shower"+Dig$(count,10)),0,0
   EntityTexture FindChild(world,"Shower"+Dig$(count,10)),tShower
   EntityAlpha FindChild(world,"Shower"+Dig$(count,10)),0.6
  Next
  no_chairs=5 : no_beds=0 : no_doors=1
  sAtmos=LoadSound("Sound/Ambience/Bathroom.wav")
 EndIf
 ;reset screen
 wOldScreen=-1
End Function

;--------------------------------------------------------------------
;//////////////////////// LOAD ATMOSPHERE ///////////////////////////
;--------------------------------------------------------------------
Function LoadAtmos()
 ;CAMERA(S)
 cam=CreateCamera()
 CameraViewport cam,0,0,GraphicsWidth(),GraphicsHeight()
 EntityType cam,5,0
 EntityRadius cam,5
 PositionEntity cam,camX#,camY#,camZ# 
 camType=1
 ;pivot
 camPivot=CreatePivot()
 PositionEntity camPivot,camPivX#,camPivY#,camPivZ#
 ;fog effect
 If gamLocation(slot)=2 Then CameraRange cam,1,2000
 CameraFogMode cam,optFog 
 CameraFogRange cam,400,1000
 If gamLocation(slot)=4 Or gamLocation(slot)=6 Or gamLocation(slot)=10 Or gamLocation(slot)=11 Then CameraFogRange cam,250,1000
 ;additional
 If screen=50
  range#=0.02
  camListener=CreateListener(cam,range#,range#,range#)
 EndIf
 dummy=CreatePivot() 
 ;lighting
 LoadLighting()
End Function

;LOAD LIGHTING
Function LoadLighting()
 ;use lights in scene
 no_lights=0
 For count=1 To 10
  limb=FindChild(world,"Light"+Dig$(count,10))
  If limb>0
   no_lights=no_lights+1
   light(no_lights)=CreateLight(3)
   PositionEntity light(no_lights),EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
   RotateEntity light(no_lights),90,0,0
   LightRange light(no_lights),500
   LightConeAngles light(no_lights),0,135
   EntityFX limb,9
   EntityShininess limb,1.0
  EndIf
 Next
 ;last resort
 If no_lights=0
  no_lights=1
  light(no_lights)=CreateLight(1)
 EndIf
End Function

;--------------------------------------------------------------------
;//////////////////// MANAGE ATMOSPHERE /////////////////////////////
;--------------------------------------------------------------------
Function ManageAtmos()
 ;time scale
 gamSpeed(slot)=1
 If gamPromo=0
  If pAnim(camFoc)=92 Or pAnim(camFoc)=132 Then gamSpeed(slot)=5
  If pAnim(camFoc)=102 And OnComputer(camFoc)=0 Then gamSpeed(slot)=5
  If pAnim(camFoc)=103 Then gamSpeed(slot)=20
 EndIf
 ;increment time
 gamSecs(slot)=gamSecs(slot)+gamSpeed(slot)
 If gamSecs(slot)=>60 Then gamMins(slot)=gamMins(slot)+1 : gamSecs(slot)=0
 If gamMins(slot)=>60 Then gamHours(slot)=gamHours(slot)+1 : gamMins(slot)=0
 If gamHours(slot)=>24
  For char=1 To no_chars
   charExperience(char)=charExperience(char)+1
   charSentence(char)=charSentence(char)-1
   If charSentence(char)<0 Then charSentence(char)=0
   If char<>gamChar(slot) And charLocation(char)>0
    v=Rnd(1,no_chars)
    If char<>v Then charRelation(char,v)=ChangeRelationship(char,v,Rnd(-1,1))
    charStrength(char)=charStrength(char)+Rnd(-2,2)
    charAgility(char)=charAgility(char)+Rnd(-2,2)
    If charRole(char)=1 Then charHappiness(char)=Rnd(50,100) Else charHappiness(char)=Rnd(10,100)
    charIntelligence(char)=charIntelligence(char)+Rnd(-2,2)
    charReputation(char)=charReputation(char)+Rnd(-2,2)
    LimitStats(char)
   EndIf
  Next
  gamHours(slot)=0 : statTim(6)=100 
 EndIf
 ;lock/open cells
 If gamMins(slot)=0 And gamSecs(slot)=0
  If gamHours(slot)=7
   ProduceSound(cam,sBuzzer,22050,1) : statTim(5)=50
   If gamPromo=0 And pAnim(gamPlayer(slot))=103 Then TriggerPromo(0,0,26)
  EndIf
  If gamHours(slot)=22
   ProduceSound(cam,sBuzzer,22050,1) : statTim(5)=50
   If InsideCell(pX#(gamPlayer(slot)),pY#(gamPlayer(slot)),pZ#(gamPlayer(slot)))<>charCell(gamChar(slot)) Or gamLocation(slot)<>TranslateBlock(charBlock(gamChar(slot)))
    If gamPromo=0 And pAnim(gamPlayer(slot))<>76 And pAnim(gamPlayer(slot))<>77 Then TriggerPromo(0,0,25)
   EndIf
  EndIf
 EndIf
 If GetBlock(gamLocation(slot))>0
  If (LockDown()=0 Or LockReady(gamLocation(slot))=0) And cellLocked(gamLocation(slot),0)=1 Then LockCells(0)
  If LockDown() And LockReady(gamLocation(slot)) And cellLocked(gamLocation(slot),0)=0 Then LockCells(1)
 EndIf
 ;serve dinner
 If gamHours(slot)=13 And gamMins(slot)=0 And gamSecs(slot)=0
  ProduceSound(cam,sBell,22050,1) : statTim(5)=50
  If gamPromo=0 And pAnim(gamPlayer(slot))<>76 And pAnim(gamPlayer(slot))<>77
   If gamLocation(slot)<>8 Or pSeat(gamPlayer(slot))=0 Then TriggerPromo(0,0,27)
  EndIf
  For tray=1 To 50
   trayState(tray)=Rnd(1,7)
  Next
 EndIf
 ;eat into trays
 If gamHours(slot)<>13 And gamMins(slot)=0 And gamSecs(slot)=0
  For tray=1 To 50
   If tray<>pSeat(gamPlayer(slot)) Or gamLocation(slot)<>8 Then trayState(tray)=trayState(tray)-1
   If trayState(tray)<0 Then trayState(tray)=0
  Next
 EndIf
 ;morning effects
 If gamHours(slot)=7 And gamMins(slot)=0 And gamSecs(slot)=0
  For cyc=1 To 6
   Repeat
    kitType(cyc)=Rnd(1,weapList)
   Until weapCreate(kitType(cyc))=1
   randy=Rnd(0,2)
   If randy=<1 Then kitState(cyc)=1 Else kitState(cyc)=0
  Next
  For char=1 To no_chars
   If char<>gamChar(slot) And charLocation(char)>0
    charHealth(char)=charHealth(char)+Rnd(10,100)
    If charHealth(char)>100 Then charHealth(char)=100
    randy=Rnd(0,10000)
    If randy=0 And charInjured(char)=0 Then charInjured(char)=Rnd(1000,80000)
   EndIf
  Next
  For v=1 To no_plays
   If pChar(v)<>gamChar(slot)
    pHealth(v)=charHealth(pChar(cyc))
    pInjured(v)=charInjured(pChar(cyc))
   EndIf
  Next
 EndIf 
 ;day properties
 If gamHours(slot)=>10 And gamHours(slot)=<16
  ambTR#=200 : ambTG#=200 : ambTB#=200
  lightTR#=220 : lightTG#=210 : lightTB#=200
  atmosTR#=200 : atmosTG#=200 : atmosTB#=240
  skyTR#=255 : skyTG#=255 : skyTB#=255 
  If gamLocation(slot)<>2 Then atmosTR#=160 : atmosTG#=130 : atmosTB#=100
 EndIf
 ;dusk/dawn properties
 If (gamHours(slot)=>7 And gamHours(slot)=<9) Or (gamHours(slot)=>17 And gamHours(slot)=<19)
  ambTR#=200 : ambTG#=190 : ambTB#=170
  lightTR#=200 : lightTG#=180 : lightTB#=160
  atmosTR#=160 : atmosTG#=130 : atmosTB#=100
  skyTR#=220 : skyTG#=200 : skyTB#=180
 EndIf
 ;twilight properties
 If (gamHours(slot)=>20 And gamHours(slot)=<22) Or (gamHours(slot)=>4 And gamHours(slot)=<6)
  ambTR#=150 : ambTG#=150 : ambTB#=150
  lightTR#=100 : lightTG#=100 : lightTB#=100
  atmosTR#=110 : atmosTG#=100 : atmosTB#=120
  skyTR#=140 : skyTG#=140 : skyTB#=160
 EndIf
 ;night properties
 If gamHours(slot)=>23 Or gamHours(slot)=<3
  ambTR#=80 : ambTG#=80 : ambTB#=80
  lightTR#=50 : lightTG#=50 : lightTB#=50
  atmosTR#=40 : atmosTG#=40 : atmosTB#=70
  skyTR#=80 : skyTG#=80 : skyTB#=100
 EndIf
 ;black-out properties
 gamBlackout(slot)=gamBlackout(slot)-gamSpeed(slot)
 If gamBlackout(slot)<0 Then gamBlackout(slot)=0
 If gamBlackout(slot)>10 And gamLocation(slot)<>2
  ambTR#=30 : ambTG#=30 : ambTB#=30
  lightTR#=10 : lightTG#=10 : lightTB#=10
  atmosTR#=0 : atmosTG#=0 : atmosTB#=0
 EndIf
 ;apply ambience
 changer#=0.025*gamSpeed(slot)
 If gotim<0 Or (gamBlackout(slot)>0 And gamLocation(slot)<>2) Then changer#=10
 If ambR#<ambTR# Then ambR#=ambR#+changer#
 If ambR#>ambTR# Then ambR#=ambR#-changer#
 If ambG#<ambTG# Then ambG#=ambG#+changer#
 If ambG#>ambTG# Then ambG#=ambG#-changer#
 If ambB#<ambTB# Then ambB#=ambB#+changer#
 If ambB#>ambTB# Then ambB#=ambB#-changer# 
 AmbientLight ambR#,ambG#,ambB#
 ;apply lighting
 If lightR#<lightTR# Then lightR#=lightR#+changer#
 If lightR#>lightTR# Then lightR#=lightR#-changer#
 If lightG#<lightTG# Then lightG#=lightG#+changer#
 If lightG#>lightTG# Then lightG#=lightG#-changer#
 If lightB#<lightTB# Then lightB#=lightB#+changer#
 If lightB#>lightTB# Then lightB#=lightB#-changer# 
 For count=1 To no_lights
  LightColor light(count),lightR#,lightG#,lightB#
  If gamBlackout(slot)>10 And gamLocation(slot)<>2
   EntityColor FindChild(world,"Light"+Dig$(count,10)),100,100,100 
   EntityFX FindChild(world,"Light"+Dig$(count,10)),0
  Else
   EntityColor FindChild(world,"Light"+Dig$(count,10)),255,255,255 
   EntityFX FindChild(world,"Light"+Dig$(count,10)),9
  EndIf
 Next
 ;apply atmosphere
 If atmosR#<atmosTR# Then atmosR#=atmosR#+changer#
 If atmosR#>atmosTR# Then atmosR#=atmosR#-changer#
 If atmosG#<atmosTG# Then atmosG#=atmosG#+changer#
 If atmosG#>atmosTG# Then atmosG#=atmosG#-changer#
 If atmosB#<atmosTB# Then atmosB#=atmosB#+changer#
 If atmosB#>atmosTB# Then atmosB#=atmosB#-changer# 
 CameraFogColor cam,atmosR#,atmosG#,atmosB#
 ;apply sky
 If skyR#<skyTR# Then skyR#=skyR#+changer#
 If skyR#>skyTR# Then skyR#=skyR#-changer#
 If skyG#<skyTG# Then skyG#=skyG#+changer#
 If skyG#>skyTG# Then skyG#=skyG#-changer#
 If skyB#<skyTB# Then skyB#=skyB#+changer#
 If skyB#>skyTB# Then skyB#=skyB#-changer# 
 If FindChild(world,"Sky")>0
  EntityColor FindChild(world,"Sky"),skyR#,skyG#,skyB#
 EndIf
End Function

;--------------------------------------------------------------------
;//////////////////// CAMERA OPERATIONS /////////////////////////////
;--------------------------------------------------------------------
Function Camera()
 ;honour collision detection
 camX#=EntityX(cam)
 camY#=EntityY(cam)
 camZ#=EntityZ(cam)
 ;timer
 camTim=camTim-1
 If camTim<0 Then camTim=0
 camRectify=camRectify-1
  If camRectify<0 Then camRectify=0 
 ;auto cam type
 If camType>0 Or camTim=0
  camType=1
  If gamPromo>0 Or screen=52 Then camType=10
  If camFoc>0 And screen=50
   If pAnim(camFoc)=>93 And pAnim(camFoc)=<95 Then camType=10
   If pAnim(camFoc)=96 Then camType=11
   If pAnim(camFoc)=>97 And pAnim(camFoc)=<98 Then camType=10
   If pAnim(camFoc)=102 Then camType=10
   If pAnim(camFoc)=132 Then camType=10
   If pAnim(camFoc)=>76 And pAnim(camFoc)=<77
    If pAnimTim(camFoc)>150 Then camType=12 Else camType=11
   EndIf
  EndIf
 EndIf 
 ;trigger manual
 ;If KeyDown(56)
  ;If KeyDown(21) And CamConflict(21)=0 Then camType=0 : camTim=100
  ;If KeyDown(23) And CamConflict(23)=0 Then camType=0 : camTim=100
  ;For count=35 To 38
   ;If KeyDown(count) And CamConflict(count)=0 Then camType=0 : camTim=100
  ;Next
 ;EndIf
 If screen=50
  camMouseX#=MouseXSpeed()/2
  camMouseY#=-(MouseYSpeed()/2)
  If camMouseX#<>0 Or camMouseY#<>0 Then camType=0 : camTim=100
  MoveMouse rX#(400),rY#(300)
 EndIf
 ;CAMERA PLACEMENT 
 ;manual control
 If camType=0
  ;If KeyDown(36) And CamConflict(36)=0 Then MoveEntity cam,-2,0,0
  ;If KeyDown(38) And CamConflict(38)=0 Then MoveEntity cam,2,0,0
  ;If KeyDown(35) And CamConflict(35)=0 Then MoveEntity cam,0,-2,0
  ;If KeyDown(21) And CamConflict(21)=0 Then MoveEntity cam,0,2,0
  ;If KeyDown(23) And CamConflict(23)=0 Then MoveEntity cam,0,0,2
  ;If KeyDown(37) And CamConflict(37)=0 Then MoveEntity cam,0,0,-2
  If MouseDown(1) Or MouseDown(2)
   If camMouseY#>0 And ReachedCord(camX#,camZ#,camPivTX#,camPivTZ#,20) And camY#=>camPivTY#-20 And camY#=<camPivTY#+5
    MoveEntity cam,camMouseX#,0,0
   Else
    MoveEntity cam,camMouseX#,0,camMouseY#
   EndIf
  Else
   MoveEntity cam,camMouseX#,camMouseY#/2,0
  EndIf
  camX#=EntityX(cam) : camTX#=camX#
  camY#=EntityY(cam) : camTY#=camY#
  camZ#=EntityZ(cam) : camTZ#=camZ#
 EndIf
 If camFoc>0
  ;over the shoulder
  If camType=1
   ResetDummy(camFoc)
   camTY#=pY#(camFoc)+40
   If pGrappler(camFoc)>0 Then zoom#=70 Else zoom#=-70
   If InsideCell(pX#(camFoc),pY#(camFoc),pZ#(camFoc))>0 Then zoom#=zoom#/2 : camTY#=camTY#-5
   MoveEntity dummy,0,0,zoom#
   camTX#=EntityX(dummy)
   camTZ#=EntityZ(dummy) 
   If GetBlock(gamLocation(slot))>0 And pBed(camFoc)>0 And pAnim(camFoc)<>101
    camTX#=GetCentre#(cellX1#(pBed(camFoc)),cellX2#(pBed(camFoc)))
    camTZ#=GetCentre#(cellZ1#(pBed(camFoc)),cellZ2#(pBed(camFoc)))
   EndIf
  EndIf 
  ;head shot
  If camType=10
   ResetDummy(camFoc)
   If camFoc=promoActor(1) Then sway#=12 Else sway#=-12
   If OnComputer(camFoc) Then sway#=12
   If pGrappling(camFoc)>0 Or pGrappler(camFoc)>0 Then zoom#=-28 Else zoom#=28
   If pPhone(camFoc)>0
    limb=FindChild(world,"Pad"+Dig$(MakePositive#(pPhone(camFoc)),10)) 
    If pZ#(camFoc)<EntityZ(limb,1) Then sway#=12 Else sway#=-12
   EndIf
   If gamPromo>0 And promoActor(1)>0 And promoActor(2)>0
    closeness#=30-GetDistance#(pX#(promoActor(1)),pZ#(promoActor(1)),pX#(promoActor(2)),pZ#(promoActor(2)))
    If closeness#<0 Then closeness#=0
    If sway#<0 Then sway#=sway#-(closeness#/2)
    If sway#>0 Then sway#=sway#+(closeness#/2)
    If zoom#<0 Then zoom#=zoom#+(closeness#/2)
    If zoom#>0 Then zoom#=zoom#-(closeness#/2)
   EndIf
   If screen=52
    If camFoc=2 Then sway#=-12 Else sway#=12
    zoom#=28
   EndIf
   MoveEntity dummy,sway#,0,zoom#
   camTX#=EntityX(dummy)  
   camTY#=pY#(camFoc)+30
   If screen=50 And pGrappler(camFoc)>0 Then camTY#=pY#(camFoc)+10
   camTZ#=EntityZ(dummy)
  EndIf
  ;dying
  If camType=11
   ResetDummy(camFoc)
   If pAnim(camFoc)=76 Then MoveEntity dummy,-15,0,20 : camTY#=pY#(camFoc)+10
   If pAnim(camFoc)=77 Then MoveEntity dummy,10,0,-25 : camTY#=pY#(camFoc)+1
   If pAnim(camFoc)=96 Then MoveEntity dummy,-20,0,30 : camTY#=pY#(camFoc)+10
   camTX#=EntityX(dummy) : camTZ#=EntityZ(dummy)
  EndIf
  ;dead
  If camType=12
   camTX#=pX#(camFoc) : camTY#=pY#(camFoc)+500 : camTZ#=pZ#(camFoc)
  EndIf
 EndIf
 ;PIVOT PLACEMENT
 If camFoc>0
  ;standard
  camPivTX#=pX#(camFoc)
  camPivTY#=pY#(camFoc)+25
  camPivTZ#=pZ#(camFoc) 
  If screen=50
   ;seated offset
   If pAnim(cyc)=102 Or pAnim(cyc)=103 Then camPivTY#=pY#(camFoc)+15
   ;projected location
   If pGrappling(camFoc)>0 Or pGrappler(camFoc)>0
    camPivTX#=EntityX(pLimb(camFoc,30),1)
    camPivTZ#=EntityZ(pLimb(camFoc,30),1)
   EndIf
   ;grapple victim
   If camType=10 And pGrappler(camFoc)>0 Then camPivTY#=pY#(camFoc)+15
   ;dying face
   If (pAnim(camFoc)=>76 And pAnim(camFoc)=<77) Or pAnim(camFoc)=96
    camPivTX#=EntityX(pLimb(camFoc,1),1)
    camPivTY#=EntityY(pLimb(camFoc,1),1)+1
    camPivTZ#=EntityZ(pLimb(camFoc,1),1)
   EndIf
  EndIf
  ;court seat offset
  If screen=52 And camFoc=5 Then camPivTY#=pY#(camFoc)+22
 EndIf
 ;NOVELTY SPEAKERS
 ;tanoy announcement
 If camFoc=0
  limb=FindChild(world,"Tanoy01") 
  PositionEntity dummy,EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
  RotateEntity dummy,EntityPitch(limb,1),EntityYaw(limb,1),EntityRoll(limb,1)
  MoveEntity dummy,15,-60,0
  camTX#=EntityX(dummy)  : camTY#=EntityY(dummy) : camTZ#=EntityZ(dummy)
  camPivTX#=EntityX(limb,1) : camPivTY#=EntityY(limb,1) : camPivTZ#=EntityZ(limb,1)
 EndIf
 ;phone call
 If camFoc<0
  limb=FindChild(world,"Pad"+Dig$(MakePositive#(camFoc),10)) 
  PositionEntity dummy,EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
  RotateEntity dummy,0,270,0
  If pZ#(gamPlayer(slot))=>EntityZ(limb,1) Then MoveEntity dummy,12,0,25
  If pZ#(gamPlayer(slot))<EntityZ(limb,1) Then MoveEntity dummy,-12,0,25
  camTX#=EntityX(dummy)  : camTY#=EntityY(dummy)-5 : camTZ#=EntityZ(dummy)
  camPivTX#=EntityX(limb,1) : camPivTY#=EntityY(limb,1) : camPivTZ#=EntityZ(limb,1)
 EndIf
 ;ENFORCE BLOCKS
 If screen=50 And camFoc>0 And camType>0 And gamPromo=0
  ;cell logic
  If GetBlock(gamLocation(slot))>0
   current=InsideCell(camX#,camY#,camZ#)
   target=InsideCell(camTX#,camTY#,camTZ#)
   If current>0 And target<>current
    If ReachedCord(camX#,camZ#,cellDoorX#(current),cellDoorZ#(current),20)=0 And CellVisible(camX#,camY#,camZ#,current)=0
     camTX#=cellDoorX#(current) : camTZ#=cellDoorZ#(current)
    EndIf
   EndIf
   If target>0 And target<>current
    If ReachedCord(camX#,camZ#,cellDoorX#(target),cellDoorZ#(target),20)=0 And CellVisible(camX#,camY#,camZ#,target)=0
     camTX#=cellDoorX#(target) : camTZ#=cellDoorZ#(target)
    EndIf
   EndIf
  EndIf
  ;help over stairs/balcony
  If GetBlock(gamLocation(slot))>0
   If (camX#<-145 Or camX#>145 Or camZ#>205) And pY#(camFoc)<100
    If camY#>100 And camTY#<130 Then camTY#=130
   EndIf
   stairProgress=pZ#(camFoc)-20
   If stairProgress<0 Then stairProgress=0
   If stairProgress>180 Then stairProgress=180
   stairPercent#=GetPercent#(stairProgress,180)
   stairY#=30+stairPercent#
   If camX#>-50 And camX#<50 And camZ#>20 And camZ#<210
    If (pX#(camFoc)<-45 Or pX#(camFoc)>45) And pZ#(camFoc)>20 And pY#(camFoc)<100
     If camTY#<stairY# Then camTY#=stairY#
    EndIf 
   EndIf 
  EndIf
  ;cubicle logic
  If gamLocation(slot)=11
   If camX#<50 Or pX#(camFoc)<50
    If camTZ#=>5 Then camTZ#=5
    If pZ#(camFoc)>5
     If pX#(camFoc)>-143 And pX#(camFoc)<-112 Then camTX#=-128
     If pX#(camFoc)>-107 And pX#(camFoc)<-76 Then camTX#=-92
     If pX#(camFoc)>-71 And pX#(camFoc)<-40 Then camTX#=-55
     If pX#(camFoc)>-34 And pX#(camFoc)<-6 Then camTX#=-20
     If pX#(camFoc)>-1 And pX#(camFoc)<29 Then camTX#=15
    EndIf
   EndIf
  EndIf
 EndIf
 ;CAMERA OPERATION
 ;camera tracking 
 If gotim>0 
  speeder=100-(gotim*2)
  If speeder<30 Then speeder=30
  If screen=52 And speeder<60 Then speeder=60
  If camType=11 And speeder<60 Then speeder=60
  If camType=12 Then speeder=480
  GetSmoothSpeeds(camX#,camTX#,camY#,camTY#,camZ#,camTZ#,speeder)
  If camType=12 Then speedY#=0.4
  If camX#<camTX# Then camX#=camX#+speedX#
  If camX#>camTX# Then camX#=camX#-speedX#
  If camY#<camTY# Then camY#=camY#+(speedY#/2)
  If camY#>camTY# Then camY#=camY#-(speedY#/2)
  If camZ#<camTZ# Then camZ#=camZ#+speedZ#
  If camZ#>camTZ# Then camZ#=camZ#-speedZ#
 EndIf
 ;pivot tracking 
 speeder=15
 If screen=52 Then speeder=30
 GetSmoothSpeeds(camPivX#,camPivTX#,camPivY#,camPivTY#,camPivZ#,camPivTZ#,speeder)
 If camPivX#<camPivTX# Then camPivX#=camPivX#+speedX#
 If camPivX#>camPivTX# Then camPivX#=camPivX#-speedX#
 If camPivY#<camPivTY# Then camPivY#=camPivY#+speedY#
 If camPivY#>camPivTY# Then camPivY#=camPivY#-speedY#
 If camPivZ#<camPivTZ# Then camPivZ#=camPivZ#+speedZ#
 If camPivZ#>camPivTZ# Then camPivZ#=camPivZ#-speedZ#
 ;position & point 
 PositionEntity camPivot,camPivX#,camPivY#,camPivZ#
 PointEntity cam,camPivot
 PositionEntity cam,camX#,camY#,camZ# 
 ;fader
 If screen=50
  If fadeAlpha#<fadeTarget# Then fadeAlpha#=fadeAlpha#+0.0025
  If fadeAlpha#>fadeTarget# Then fadeAlpha#=fadeAlpha#-0.0025
  If fadeAlpha#<0 Then fadeAlpha#=0
  If fadeAlpha#>1.0 Then fadeAlpha#=1.0
  PositionEntity dummy,camX#,camY#,camZ#
  RotateEntity dummy,EntityPitch(cam),EntityYaw(cam),EntityRoll(cam)
  MoveEntity dummy,0,0,3
  PositionEntity fader,EntityX(dummy),EntityY(dummy),EntityZ(dummy)
  EntityAlpha fader,fadeAlpha# 
 EndIf
End Function

;ZOOM CAMERA TO TARGET
Function ZoomCamera()
 camX#=camTX#
 camY#=camTY#
 camZ#=camTZ#
End Function

;RESET DUMMY (in terms of target)
Function ResetDummy(cyc)
 PositionEntity dummy,pX#(cyc),pY#(cyc),pZ#(cyc)
 RotateEntity dummy,0,pA#(cyc),0
End Function

;CAMERA vs CONTROL CONFLICTS?
Function CamConflict(command)
 conflict=0
 If command=keyShoot Or command=keyPass Or command=keyLob Then conflict=1
 Return conflict
End Function

;ENTER DOOR
Function EnterDoor(cyc,door)
 ;store current location
 oldLocation=gamLocation(slot)
 ;north >>> hall
 If gamLocation(slot)=1
  charX#(pChar(cyc))=-150 : charZ#(pChar(cyc))=280
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=180
  charLocation(pChar(cyc))=9
 EndIf
 ;yard >>> hall
 If gamLocation(slot)=2
  charX#(pChar(cyc))=150 : charZ#(pChar(cyc))=280
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=180
  charLocation(pChar(cyc))=9
 EndIf
 ;east >>> hall
 If gamLocation(slot)=3
  charX#(pChar(cyc))=280 : charZ#(pChar(cyc))=150
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=90
  charLocation(pChar(cyc))=9
 EndIf
 ;study >>> hall
 If gamLocation(slot)=4 And door=1
  charX#(pChar(cyc))=280 : charZ#(pChar(cyc))=-150
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=90
  charLocation(pChar(cyc))=9
 EndIf
 ;study >>> workshop
 If gamLocation(slot)=4 And door=2
  charX#(pChar(cyc))=0 : charZ#(pChar(cyc))=-105
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=10
 EndIf
 ;south >>> hall
 If gamLocation(slot)=5
  charX#(pChar(cyc))=150 : charZ#(pChar(cyc))=-280
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=9
 EndIf
 ;hospital >>> hall
 If gamLocation(slot)=6
  charX#(pChar(cyc))=-150 : charZ#(pChar(cyc))=-280
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=9
 EndIf
 ;hospital >>> toilets
 If gamLocation(slot)=6 And door=2
  charX#(pChar(cyc))=90 : charZ#(pChar(cyc))=-55
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=11
 EndIf
 ;west >>> hall
 If gamLocation(slot)=7
  charX#(pChar(cyc))=-280 : charZ#(pChar(cyc))=-150
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=270
  charLocation(pChar(cyc))=9
 EndIf
 ;kitchen >>> hall
 If gamLocation(slot)=8
  charX#(pChar(cyc))=-280 : charZ#(pChar(cyc))=150
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=270
  charLocation(pChar(cyc))=9
 EndIf
 ;hall >>> block
 If gamLocation(slot)=9 And (door=1 Or door=3 Or door=5 Or door=7)
  charX#(pChar(cyc))=0 : charZ#(pChar(cyc))=-325
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=door
 EndIf
 ;hall >>> yard
 If gamLocation(slot)=9 And door=2
  charX#(pChar(cyc))=80 : charZ#(pChar(cyc))=215
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=door
 EndIf
 ;hall >>> study
 If gamLocation(slot)=9 And door=4
  charX#(pChar(cyc))=5 : charZ#(pChar(cyc))=-130
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=4
 EndIf
 ;hall >>> hospital
 If gamLocation(slot)=9 And door=6
  charX#(pChar(cyc))=0 : charZ#(pChar(cyc))=-130
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=6
 EndIf
 ;hall >>> kitchen
 If gamLocation(slot)=9 And door=8
  charX#(pChar(cyc))=0 : charZ#(pChar(cyc))=-330
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=0
  charLocation(pChar(cyc))=8
 EndIf
 ;workshop >>> study
 If gamLocation(slot)=10
  charX#(pChar(cyc))=135 : charZ#(pChar(cyc))=0
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=90
  charLocation(pChar(cyc))=4
 EndIf
 ;toilets >>> hospital
 If gamLocation(slot)=11
  charX#(pChar(cyc))=0 : charZ#(pChar(cyc))=130
  charY#(pChar(cyc))=20 : charA#(pChar(cyc))=180
  charLocation(pChar(cyc))=6
 EndIf
 ;proceed
 pDoorFriction(cyc,door)=0
 go=1 
End Function

;RELOCATE CPU CHARACTERS
Function RelocateChars()
 For char=1 To no_chars
  If char<>gamChar(slot) And charRole(char)=<1 And charLocation(char)>0
   ;get new destination
   target=0 : source=charLocation(char)
   randy=Rnd(0,20)
   If charRole(char)=1 And AreaPopulation(charLocation(char),1)>2 Then randy=Rnd(0,10)  
   If charLocation(char)=8 And (gamHours(slot)<12 Or gamHours(slot)>14) Then randy=Rnd(0,10)
   If randy=0 And charLocation(char)=>1 And charLocation(char)=<8 Then target=9
   If randy=0 And charLocation(char)=9 Then target=Rnd(1,8)
   If randy=1 And charLocation(char)=9 Then target=2
   If randy=1 And charLocation(char)=4 Then target=10
   If randy=1 And charLocation(char)=10 Then target=4
   If randy=1 And charLocation(char)=6 Then target=11
   If randy=1 And charLocation(char)=11 Then target=6
   If randy=2 And charRole(char)=0 And charLocation(char)=9 Then target=TranslateBlock(charBlock(char))
   If randy=3 Or charY#(char)<0 Then target=Rnd(1,11)
   If randy=>4 And randy=<5 And gamHours(slot)=>12 And gamHours(slot)=<14 Then target=8
   ;consider population
   If target=4 Or target=6 Or target=10 Or target=11
    If AreaPopulation(target,-1)=>10 Then target=0
    If charRole(char)=1 And AreaPopulation(target,1)=>2 Then target=0
   EndIf
   If AreaPopulation(target,-1)=>20 Then target=0
   If charRole(char)=1 And AreaPopulation(charLocation(char),1)=<1 And charLocation(char)<>11 Then target=0
   If charRole(char)=1 And AreaPopulation(target,1)=>4 Then target=0
   If charRole(char)=1 And target=11 Then target=0
   ;force back to cell
   If LockDown() Or (gamHours(slot)=21 And gamMins(slot)=>30)
    If charRole(char)=0 Then target=TranslateBlock(charBlock(char))
   EndIf
   ;mission blocks
   If gamMission(slot)>0 And char=gamClient(slot) Then target=0
   If gamMission(slot)=11 Or gamMission(slot)=12 Or gamMission(slot)=16
    If char=gamTarget(slot) Then target=0
   EndIf
   ;shadow runner
   If charFollowTim(char)>0 Then target=charLocation(gamChar(slot))
   ;deliver to destination
   If target>0 And target<>source
    ;north >>> hall
    If source=1 And target=9 
     charX#(char)=Rnd(-200,-100) : charZ#(char)=Rnd(230,280)
     charY#(char)=20 : charA#(char)=Rnd(135,225)
    EndIf
    ;yard >>> hall
    If source=2 And target=9 
     charX#(char)=Rnd(100,200) : charZ#(char)=Rnd(230,280)
     charY#(char)=20 : charA#(char)=Rnd(135,225)
    EndIf
    ;east >>> hall
    If source=3 And target=9 
     charX#(char)=Rnd(230,280) : charZ#(char)=Rnd(100,200)
     charY#(char)=20 : charA#(char)=Rnd(45,135)
    EndIf
    ;study >>> hall
    If (source=4 Or source=10) And target=9 
     charX#(char)=Rnd(230,280) : charZ#(char)=Rnd(-200,-100)
     charY#(char)=20 : charA#(char)=Rnd(45,135)
    EndIf
    ;study >>> workshop
    If target=10 
     charX#(char)=Rnd(-25,25) : charZ#(char)=Rnd(-105,-55)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;south >>> hall
    If source=5 And target=9 
     charX#(char)=Rnd(100,200) : charZ#(char)=Rnd(-280,-230)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;hospital >>> hall
    If (source=6 Or source=11) And target=9 
     charX#(char)=Rnd(-200,-100) : charZ#(char)=Rnd(-280,-230)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;hospital >>> toilets
    If target=11 
     charX#(char)=Rnd(40,140) : charZ#(char)=Rnd(-55,-5)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;west >>> hall
    If source=7 And target=9 
     charX#(char)=Rnd(-280,-230) : charZ#(char)=Rnd(-200,-100)
     charY#(char)=20 : charA#(char)=Rnd(225,315)
    EndIf
    ;kitchen >>> hall
    If source=8 And target=9 
     charX#(char)=Rnd(-280,-230) : charZ#(char)=Rnd(100,200)
     charY#(char)=20 : charA#(char)=Rnd(225,315)
    EndIf
    ;hall >>> block
    If GetBlock(target)>0
     charX#(char)=Rnd(-50,50) : charZ#(char)=Rnd(-325,-275)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;hall >>> yard
    If target=2
     charX#(char)=Rnd(30,130) : charZ#(char)=Rnd(215,265)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;hall >>> study
    If source<>10 And target=4
     charX#(char)=Rnd(-45,55) : charZ#(char)=Rnd(-130,-80)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;hall >>> hospital
    If source<>11 And target=6
     charX#(char)=Rnd(-50,50) : charZ#(char)=Rnd(-130,-80)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;hall >>> kitchen
    If target=8
     charX#(char)=Rnd(-50,50) : charZ#(char)=Rnd(-330,-180)
     charY#(char)=20 : charA#(char)=Rnd(-45,45)
    EndIf
    ;workshop >>> study
    If source=10 And target=4
     charX#(char)=Rnd(85,135) : charZ#(char)=Rnd(-25,25)
     charY#(char)=20 : charA#(char)=Rnd(45,135)
    EndIf
    ;toilet >>> hospital
    If source=11 And target=10
     charX#(char)=Rnd(-50,50) : charZ#(char)=Rnd(80,130)
     charY#(char)=20 : charA#(char)=Rnd(135,225)
    EndIf
    ;place inside cell
    If charRole(char)=0 And LockDown() And GetBlock(target)>0 And target<>gamLocation(slot)
     charX#(char)=GetCentre#(cellX1#(charCell(char)),cellX2#(charCell(char)))
     charZ#(char)=GetCentre#(cellZ1#(charCell(char)),cellZ2#(charCell(char)))
     charY#(char)=cellY1#(charCell(char))+20 : charA#(char)=Rnd(0,360)
    EndIf
    ;update location
    charLocation(char)=target
   EndIf
   ;released
   If charRole(char)=0 And charSentence(char)=<0 And (char<>gamClient(slot) Or gamMission(slot)=0)
    charLocation(char)=0 : gamRelease(slot)=char
    RuinMission(char)
    For v=1 To no_chars
     If v<>gamChar(slot) And charPromo(v,gamChar(slot))=0 And charRelation(v,gamChar(slot))=>0 And charAngerTim(v,gamChar(slot))=0
      If charRelation(gamChar(slot),pChar(cyc))>0 And charRelation(v,pChar(cyc))>0 Then charPromo(v,gamChar(slot))=219 : charPromoRef(v)=char
      If charRelation(gamChar(slot),pChar(cyc))<0 And charRelation(v,pChar(cyc))<0 Then charPromo(v,gamChar(slot))=220 : charPromoRef(v)=char
     EndIf
    Next
   EndIf
   ;make weapon follow
   If charWeapon(char)>0 Then weapLocation(charWeapon(char))=charLocation(char)
  EndIf
  ;introduce new characters
  randy=Rnd(0,10)
  If randy=0 And char<>gamChar(slot) And charRole(char)=<1 And charLocation(char)=0 And char<>gamFatality(slot) And char<>gamRelease(slot) And gamArrival(slot)=0
   If GamePopulation(1)<optPopulation/5 Then role=1 Else role=0
   GenerateCharacter(char,role)
   If charLocation(char)>0 Then gamArrival(slot)=char
   If role=0
    randy=Rnd(0,4)
    If randy=1 Or (randy=0 And charReputation(char)<charReputation(gamChar(slot))) Then charPromo(char,gamChar(slot))=200
    If randy=2 Or (randy=0 And charReputation(char)=>charReputation(gamChar(slot))) Then charPromo(char,gamChar(slot))=201
    If charRole(char)=0 And charCell(char)=charCell(gamChar(slot)) And charBlock(char)=charBlock(gamChar(slot))
     randy=Rnd(0,2)
     If randy=1 Or (randy=0 And charReputation(char)<charReputation(gamChar(slot))) Then charPromo(char,gamChar(slot))=Rnd(202,203)
     If randy=2 Or (randy=0 And charReputation(char)=>charReputation(gamChar(slot))) Then charPromo(char,gamChar(slot))=Rnd(203,204)
    EndIf
   EndIf
  EndIf
  ;change gang affilliations
  oldGang=charGang(char)
  randy=Rnd(0,1000)
  If randy=>1 And randy=<6 And char<>gamChar(slot) And charRole(char)=0 And charLocation(char)>0
   If charGang(char)=0
    If randy=1 And charGangHistory(char,1)=0 And GetRace(char)=0 Then ChangeGang(char,1)
    If randy=2 And charGangHistory(char,2)=0 And GetRace(char)=1 Then ChangeGang(char,2)
    If randy=3 And charGangHistory(char,3)=0 And GetRace(char)=2 Then ChangeGang(char,3)
    If randy=4 And charGangHistory(char,4)=0 And charIntelligence(char)>70 Then ChangeGang(char,4)
    If randy=5 And charGangHistory(char,5)=0 And charStrength(char)+charAgility(char)>140 Then ChangeGang(char,5)
    If randy=6 And charGangHistory(char,6)=0 And charReputation(char)<70 Then ChangeGang(char,6)
   Else
    ChangeGang(char,0)
   EndIf 
   If charGang(char)<>oldGang And charPromo(char,gamChar(slot))=0
    If oldGang=charGang(gamChar(slot)) And charRelation(char,gamChar(slot))=1 Then charPromo(char,gamChar(slot))=45 ;left gang
    If charGang(char)>0 And charGang(char)<>charGang(gamChar(slot)) And charRelation(char,gamChar(slot))=1
     charPromo(char,gamChar(slot))=46 ;friend joins a gang
    EndIf
    If charGang(char)>0 And charGang(char)=charGang(gamChar(slot)) Then charPromo(char,gamChar(slot))=47 ;new member to your gang
   EndIf
  EndIf
 Next
End Function

;COUNT AREA POPULATION
Function AreaPopulation(area,role) ;-1=any
 value=0
 For char=1 To no_chars
  If charRole(char)=role Or role=-1
   If charLocation(char)=area Then value=value+1
  EndIf
 Next
 Return value
End Function

;COUNT CELL POPULATION
Function CellPopulation(block,cell)
 value=0
 For char=1 To no_chars
  If charBlock(char)=block And charCell(char)=cell Then value=value+1
 Next
 Return value
End Function

;COUNT GAME POPULATION
Function GamePopulation(role) ;-1=any
 value=0
 For char=1 To no_chars
  If charRole(char)=role Or role=-1 Then value=value+1
 Next
 Return value
End Function

;GET BLOCK (FROM LOCATION)
Function GetBlock(area)
 block=0
 If area=1 Then block=1
 If area=3 Then block=2
 If area=5 Then block=3
 If area=7 Then block=4
 Return block
End Function

;GET BLOCK LOCATION (FROM ID)
Function TranslateBlock(block)
 area=0
 If block=1 Then area=1
 If block=2 Then area=3
 If block=3 Then area=5
 If block=4 Then area=7
 Return area
End Function

;LOCK DOWN TIME?
Function LockDown()
 value=0
 If gamHours(slot)<7 Or gamHours(slot)=>22 Then value=1
 Return value
End Function

;CELLS READY TO BE LOCKED?
Function LockReady(area)
 value=1
 For v=1 To no_plays
  ;not in cell
  If charRole(pChar(v))=0 And (GetBlock(area)=charBlock(pChar(v)) Or pChar(v)<>gamChar(slot))
   If InsideCell(pX#(v),pY#(v),pZ#(v))<>charCell(pChar(v)) Then value=0
  EndIf
  ;cell occupied by illegal
  If charRole(pChar(v))=1 Or GetBlock(area)<>charBlock(pChar(v))
   If InsideCell(pX#(v),pY#(v),pZ#(v))>0 Then value=0
  EndIf
 Next
 ;open for warrant
 If gamWarrant(slot)>0 Then value=0
 Return value
End Function

;LOCK CELLS
Function LockCells(lock) ;0=open, 1=close
 ProduceSound(cam,sDoor(3),22050,1)
 If lock=0 Then Animate world,3,4.0
 If lock=1 Then Animate world,3,-4.0
 For count=0 To 20
  cellLocked(gamLocation(slot),count)=lock
 Next
End Function

;INSIDE CELL?
Function InsideCell(x#,y#,z#)
 ;find matches
 cell=0
 For count=1 To 20
  If y#=>cellY1#(count) And y#=<cellY2#(count)
   If x#=>cellX1#(count) And x#=<cellX2#(count) And z#=>cellZ1#(count) And z#=<cellZ2#(count) Then cell=count
  EndIf
 Next
 ;null if not in block
 If screen=50 And go=0
  If GetBlock(gamLocation(slot))=0 Then cell=0
 EndIf
 Return cell
End Function

;IN LINE WITH CELL?
Function CellVisible(x#,y#,z#,cell)
 value=0
 If GetBlock(gamLocation(slot))>0 And cell>0
  If y#=>cellY1#(cell) And y#=<cellY2#(cell)
   If cell=5 Or cell=6 Or cell=15 Or cell=16
    If x#=>cellX1#(cell) And x#=<cellX2#(cell) Then value=1
   Else
    If z#=>cellZ1#(cell) And z#=<cellZ2#(cell) Then value=1
   EndIf
  EndIf
 EndIf
 Return value
End Function

;IN PROXIMITY OF CHAIR?
Function ChairProximity(cyc,chair)
 value=0
 limb=FindChild(world,"Chair"+Dig$(chair,10))
 If pX#(cyc)>EntityX(limb,1)-18 And pX#(cyc)<EntityX(limb,1)+18 And pY#(cyc)>EntityY(limb,1)-30 And pY#(cyc)<EntityY(limb,1)-5 And pZ#(cyc)>EntityZ(limb,1)-18 And pZ#(cyc)<EntityZ(limb,1)+18
  back=0 
  If gamLocation(slot)<>11
   PositionEntity dummy,EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
   RotateEntity dummy,0,EntityYaw(limb,1),0
   MoveEntity dummy,0,0,-30
   If pX#(cyc)>EntityX(dummy,1)-20 And pX#(cyc)<EntityX(dummy,1)+20 And pZ#(cyc)>EntityZ(dummy,1)-20 And pZ#(cyc)<EntityZ(dummy,1)+20 Then back=1 
  EndIf
  If back=0 And InLine(cyc,limb,45) Then value=1
 EndIf
 Return value
End Function

;CHAIR TAKEN?
Function ChairTaken(chair)
 value=0
 For v=1 To no_plays
  If pSeat(v)=chair Then value=1
 Next
 Return value
End Function

;IN PROXIMITY OF BED?
Function BedProximity(cyc,bed)
 value=0
 limb=FindChild(world,"Mat"+Dig$(bed,10))
 If pX#(cyc)>EntityX(limb,1)-25 And pX#(cyc)<EntityX(limb,1)+25 And pY#(cyc)>EntityY(limb,1)-25 And pY#(cyc)<EntityY(limb,1) And pZ#(cyc)>EntityZ(limb,1)-25 And pZ#(cyc)<EntityZ(limb,1)+25
  If InLine(cyc,limb,45) Then value=1
 EndIf
 Return value
End Function

;BED TAKEN?
Function BedTaken(bed)
 value=0
 For v=1 To no_plays
  If pBed(v)=bed Then value=1
 Next
 Return value
End Function

;IN PROXIMITY OF PHONE?
Function PhoneProximity(cyc)
 value=0
 If gamLocation(slot)=9
  For v=1 To 4
   limb=FindChild(world,"Pad"+Dig$(v,10))
   If pX#(cyc)>EntityX(limb,1)-20 And pX#(cyc)<EntityX(limb,1)+20 And pZ#(cyc)>EntityZ(limb,1)-15 And pZ#(cyc)<EntityZ(limb,1)+15
    value=v
   EndIf
  Next
 EndIf
 Return value
End Function

;PHONE TAKEN?
Function PhoneTaken(phone)
 value=0
 For v=1 To no_plays
  If pPhone(v)=phone Then value=1
 Next
 Return value
End Function

;ON COMPUTER?
Function OnComputer(cyc)
 value=0
 If gamLocation(slot)=4 And pSeat(cyc)=5 Then value=1
 If gamLocation(slot)=6 And pSeat(cyc)=5 Then value=1
 If gamLocation(slot)=9 And pSeat(cyc)=7 Then value=1
 Return value
End Function

;NEAR BASKET
Function NearBasket(cyc)
 value=0
 If gamLocation(slot)=2 And pWeapon(cyc)>0
  limb=FindChild(world,"Rim")
  If pX#(cyc)>EntityX(limb,1)-100 And pX#(cyc)<EntityX(limb,1)+100 And pZ#(cyc)>EntityZ(limb,1)-100 And pZ#(cyc)<EntityZ(limb,1)+100
   If InLine(cyc,limb,60) Then value=1
  EndIf
 EndIf
 Return value
End Function