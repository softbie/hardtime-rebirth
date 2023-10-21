;//////////////////////////////////////////////////////////////////////////////
;--------------------------- HARD TIME: VARIABLES -----------------------------
;//////////////////////////////////////////////////////////////////////////////

;//////////////////////////////////////////////////////
;------------------ STRUCTURE -------------------------
;//////////////////////////////////////////////////////
Global version=4
Global screen,oldScreen
Global screenSource,screenAgenda
Global screenCall,callX,callY
Global go,gotim
Global foc,subfoc
Global timer,keytim
Global file,saver,loader
Global tester

;//////////////////////////////////////////////////////
;------------------- PROGRESS -------------------------
;//////////////////////////////////////////////////////
.Progress
Global slot=1,oldLocation
Global gamPoints,gamPointLimit
Global gamPause,gamFile,gamDoor
Global gamEnded
Dim gamName$(3)
Dim gamPhoto(3)
Dim gamChar(3)
Dim gamPlayer(3)
Dim gamLocation(3)
Dim gamMoney(3)
Dim gamSpeed(3)
Dim gamSecs(3)
Dim gamMins(3)
Dim gamHours(3)
;handles
Dim gamWarrant(3)
Dim gamVictim(3)
Dim gamItem(3)
Dim gamArrival(3)
Dim gamFatality(3)
Dim gamRelease(3)
Dim gamEscape(3)
Dim gamGrowth(3)
Dim gamBlackout(3)
Dim gamBombThreat(3)
;missions
Dim gamMission(3)
Dim gamClient(3)
Dim gamTarget(3)
Dim gamDeadline(3)
Dim gamReward(3)
;stat highlighters (1=strength, 2=agility, 3=intell, 4=rep, 5=time, 6=sentence, 7=money)
Dim statTim(10)
;promos
Global gamPromo,no_promos=300
Global promoTim,promoStage ;0=intro, 1=option, 2=positive response, 3=negative response
Global promoEffect,promoVariable
Global promoAccuser,promoVerdict,promoCash
Global optionA$,optionB$
Dim promoActor(2)
Dim promoReact(10)
Dim promoUsed(300)
;phones
Global phoneRing,phoneTim,phonePromo
Dim phoneX#(4),phoneY#(4),phoneZ#(4)
;outro
Dim endChar(10)
Dim endFate(10)

;//////////////////////////////////////////////////////
;------------------- OPTIONS --------------------------
;//////////////////////////////////////////////////////
.Options
;constants
Global optPlayLim=50,optCharLim=200
Global optWeapLim=100
Global optPopulation=60
;preferences
Global optRes=2,optFog=1
Global optFX=1,optShadows=2
Global optGore=3 ;0=none, 1=scars, 2=pools, 3=limbs
;keys & buttons
Global keyAttack=30,keyDefend=44,keyThrow=31,keyPickUp=45
Global buttAttack=1,buttDefend=3,buttThrow=2,buttPickUp=4

;///////////////////////////////////////////////////////
;----------------------- SOUND -------------------------
;///////////////////////////////////////////////////////
.Sound
;music & atmosphere
Global sTheme
Global chTheme,musicVol#
Global chAtmos,sAtmos
Global chPhone,chAlarm
;menu effects
Global sMenuBrowse=LoadSound("Sound/Browse.wav")
Global sMenuSelect=LoadSound("Sound/Select.wav")
Global sMenuGo=LoadSound("Sound/Confirm.wav")
Global sMenuBack=LoadSound("Sound/Cancel.wav")
Global sVoid=LoadSound("Sound/Void.wav")
Global sTrash=LoadSound("Sound/Trash.wav")
Global sCamera=LoadSound("Sound/Camera.wav")
Global sComputer=LoadSound("Sound/Computer.wav")
Global sCash=LoadSound("Sound/Cash.wav")
Global sPaper=LoadSound("Sound/Paper.wav")
;court reactions
Global sMurmur=LoadSound("Sound/Murmur.wav")
Dim sJury(2)
sJury(1)=LoadSound("Sound/Cheer.wav")
sJury(2)=LoadSound("Sound/Jeer.wav")
;world
Dim sDoor(3)
For count=1 To 3
 sDoor(count)=Load3DSound("Sound/World/Door0"+count+".wav")
Next
Global sBuzzer=Load3DSound("Sound/World/Buzzer.wav")
Global sBell=Load3DSound("Sound/World/Bell.wav")
Global sRing=Load3DSound("Sound/World/Ring.wav")
Global sAlarm=Load3DSound("Sound/World/Alarm.wav")
Global sTanoy=Load3DSound("Sound/World/Tanoy.wav")
Global sBasket=Load3DSound("Sound/World/Basket.wav")
;movements
Global sFall=Load3DSound("Sound/Movement/Fall.wav")
Global sThud=Load3DSound("Sound/Movement/Thud.wav")
Dim sShuffle(3)
For count=1 To 3
 sShuffle(count)=Load3DSound("Sound/Movement/Shuffle0"+count+".wav")
Next
Dim sStep(6)
For count=1 To 4
 sStep(count)=Load3DSound("Sound/Movement/Step0"+count+".wav")
Next
;pain
Global chDeath
Global sDeath=Load3DSound("Sound/Pain/Death.wav")
Global sChoke=Load3DSound("Sound/Pain/Choke.wav")
Global sSnore=Load3DSound("Sound/Pain/Snoring.wav")
Global sBreakdown=Load3DSound("Sound/Pain/Breakdown.wav")
Dim sPain(10)
For count=1 To 8
 sPain(count)=Load3DSound("Sound/Pain/Pain0"+count+".wav")
Next
Dim sAgony(5)
For count=1 To 3
 sAgony(count)=Load3DSound("Sound/Pain/Agony0"+count+".wav")
Next
;impacts
Global sSwing=Load3DSound("Sound/Movement/Swing.wav")
Global sBleed=Load3DSound("Sound/Props/Bleed.wav")
Global sStab=Load3DSound("Sound/Props/Stab.wav")
Global sEat=Load3DSound("Sound/Props/Eat.wav")
Global sDrink=Load3DSound("Sound/Props/Drink.wav")
Dim sImpact(6)
For count=1 To 6
 sImpact(count)=Load3DSound("Sound/Movement/Impact0"+count+".wav")
Next
;weapons
Global sGeneric=Load3DSound("Sound/Props/Generic.wav")
Global sBlade=Load3DSound("Sound/Props/Blade.wav")
Global sMetal=Load3DSound("Sound/Props/Metal.wav")
Global sWood=Load3DSound("Sound/Props/Wood.wav")
Global sCane=Load3DSound("Sound/Props/Cane.wav")
Global sString=Load3DSound("Sound/Props/String.wav")
Global sRock=Load3DSound("Sound/Props/Rock.wav")
Global sAxe=Load3DSound("Sound/Props/Axe.wav")
Global sBall=Load3DSound("Sound/Props/Ball.wav")
Global sPhone=Load3DSound("Sound/Props/Phone.wav")
Global sCigar=Load3DSound("Sound/Props/Cigar.wav")
Global sSyringe=Load3DSound("Sound/Props/Syringe.wav")
Global sBottle=Load3DSound("Sound/Props/Bottle.wav")
Global sSplash=Load3DSound("Sound/Props/Splash.wav")
;technology
Dim sShot(5)
For count=1 To 2
 sShot(count)=Load3DSound("Sound/Props/Shot0"+count+".wav")
Next
Dim sRicochet(5)
For count=1 To 3
 sRicochet(count)=Load3DSound("Sound/Props/Ricochet0"+count+".wav")
Next
Global sReload=Load3DSound("Sound/Props/Reload.wav")
Global sGun=Load3DSound("Sound/Props/Gun.wav")
Global sMine=Load3DSound("Sound/Props/Mine.wav")
Global sExplosion=Load3DSound("Sound/Props/Explosion.wav")
Global sBlaze=Load3DSound("Sound/Props/Blaze.wav")
Global sLaser=Load3DSound("Sound/Props/Laser.wav")

;//////////////////////////////////////////////////////
;-------------------- PLAYERS -------------------------
;//////////////////////////////////////////////////////
.Players
Global no_plays
Dim p(optPlayLim)
Dim pPivot(optPlayLim)
Dim pMovePivot(optPlayLim)
Dim pFoc(optPlayLim)
Dim pX#(optPlayLim)
Dim pY#(optPlayLim)
Dim pZ#(optPlayLim)
Dim pOldX#(optPlayLim)
Dim pOldY#(optPlayLim)
Dim pOldZ#(optPlayLim)
Dim pA#(optPlayLim)
Dim pTA#(optPlayLim)
Dim pGrappling(optPlayLim)
Dim pGrappler(optPlayLim)
Dim pCollisions(optPlayLim)
Dim pOldMoveX#(optPlayLim)
Dim pOldMoveZ#(optPlayLim)
;scenery interaction
Dim pPhone(optPlayLim)
Dim pBed(optPlayLim)
Dim pSeat(optPlayLim)
Dim pSeatX#(optPlayLim)
Dim pSeatY#(optPlayLim)
Dim pSeatZ#(optPlayLim)
Dim pSeatA#(optPlayLim)
Dim pLeaveX#(optPlayLim)
Dim pLeaveY#(optPlayLim)
Dim pLeaveZ#(optPlayLim)
Dim pLeaveA#(optPlayLim)
Dim pSeatFriction(optPlayLim,50)
Dim pBedFriction(optPlayLim,20)
Dim pDoorFriction(optPlayLim,10)
Dim pFoodTim(optPlayLim)
;physics
Dim pGround#(optPlayLim)
Dim pHurtA#(optPlayLim)
Dim pStagger#(optPlayLim)
Dim pSpeed#(optPlayLim)
Dim pGravity#(optPlayLim)
Dim pCharge#(optPlayLim)
;status
Dim pSting(optPlayLim)
Dim pMultiSting(optPlayLim,optPlayLim)
Dim pHealth(optPlayLim)
Dim pHealthLimit(optPlayLim)
Dim pOldHealth(optPlayLim)
Dim pInjured(optPlayLim)
Dim pDazed(optPlayLim)
Dim pHP(optPlayLim)
Dim pDT(optPlayLim)
Dim pWeapon(optPlayLim)
Dim pWeaponTim(optPlayLim,optWeapLim)
;input
Dim pControl(optPlayLim)
Dim cUp(optPlayLim)
Dim cDown(optPlayLim)
Dim cLeft(optPlayLim)
Dim cRight(optPlayLim)
Dim cAttack(optPlayLim)
Dim cDefend(optPlayLim)
Dim cThrow(optPlayLim)
Dim cPickUp(optPlayLim)
Dim pFireTim(optPlayLim)
;AI
Dim pAgenda(optPlayLim)
Dim pOldAgenda(optPlayLim)
Dim pTarget(optPlayLim)
Dim pTX#(optPlayLim)
Dim pTY#(optPlayLim)
Dim pTZ#(optPlayLim)
Dim pExploreX#(optPlayLim)
Dim pExploreY#(optPlayLim)
Dim pExploreZ#(optPlayLim)
Dim pSubX#(optPlayLim)
Dim pSubZ#(optPlayLim)
Dim pExploreRange(optPlayLim)
Dim pSatisfied(optPlayLim)
Dim pNowhere(optPlayLim)
Dim pRunTim(optPlayLim)
Dim pFollowFoc(optPlayLim)
Dim pWeapFoc(optPlayLim)
Dim pInteract(optPlayLim,optPlayLim)
;animation
Dim pSeq(optPlayLim,620)
Dim pState(optPlayLim)
Dim pPromoState(optPlayLim)
Dim pAnim(optPlayLim)
Dim pOldAnim(optPlayLim)
Dim pAnimTim(optPlayLim)
Dim pAnimSpeed#(optPlayLim)
Dim pStepTim(optPlayLim)
;appearance
Dim pChar(optPlayLim)
Dim pEyes(optPlayLim)
Dim pOldEyes(optPlayLim)
Dim pMouth(optPlayLim)
Dim pSpeaking(optPlayLim)
Dim pShadow(optPlayLim,40)
Dim pControlTim(optPlayLim)
Dim pTag(2)
Dim pHighlight(2)

;///////////////////////////////////////////////////////
;----------------------- LIMBS -------------------------
;///////////////////////////////////////////////////////
.Limbs
;status
Dim pLimb(optPlayLim,40)
Dim pScar(optPlayLim,40)
Dim pOldScar(optPlayLim,40)
;heirarchy
Dim limbPrecede(40)
Dim limbSource(40)
;left arm
limbPrecede(4)=5 : limbSource(4)=3 ;left bicep
limbPrecede(5)=6 : limbSource(5)=4 ;left arm
limbPrecede(6)=0 : limbSource(6)=5 ;left hand
limbPrecede(7)=8 : limbSource(7)=6 ;left thumb01
limbPrecede(8)=0 : limbSource(8)=7 ;left thumb02
limbPrecede(9)=10 : limbSource(9)=6 ;left finger01
limbPrecede(10)=0 : limbSource(10)=9 ;left finger02
limbPrecede(11)=12 : limbSource(11)=6 ;left finger03
limbPrecede(12)=0 : limbSource(12)=11 ;left finger04
limbPrecede(13)=14 : limbSource(13)=6 ;left finger05
limbPrecede(14)=0 : limbSource(14)=13 ;left finger06
limbPrecede(15)=16 : limbSource(15)=6 ;left finger07
limbPrecede(16)=0 : limbSource(16)=15 ;left finger08
;right arm
limbPrecede(17)=18 : limbSource(17)=3 ;right bicep
limbPrecede(18)=19 : limbSource(18)=17 ;right arm
limbPrecede(19)=0 : limbSource(19)=18 ;right hand
limbPrecede(20)=21 : limbSource(20)=19 ;right thumb01
limbPrecede(21)=0 : limbSource(21)=20 ;right thumb02
limbPrecede(22)=23 : limbSource(22)=19 ;right finger01
limbPrecede(23)=0 : limbSource(23)=22 ;right finger02
limbPrecede(24)=25 : limbSource(24)=19 ;right finger03
limbPrecede(25)=0 : limbSource(25)=24 ;right finger04
limbPrecede(26)=27 : limbSource(26)=19 ;right finger05
limbPrecede(27)=0 : limbSource(27)=26 ;right finger06
limbPrecede(28)=29 : limbSource(28)=19 ;right finger07
limbPrecede(29)=0 : limbSource(29)=28 ;right finger08
;legs
limbPrecede(31)=32 : limbSource(31)=30 ;left thigh
limbPrecede(32)=33 : limbSource(32)=31 ;left leg
limbPrecede(33)=0 : limbSource(33)=32 ;left foot
limbPrecede(34)=35 : limbSource(34)=30 ;right thigh
limbPrecede(35)=36 : limbSource(35)=34 ;right leg
limbPrecede(36)=0 : limbSource(36)=35 ;right foot
;additional
limbPrecede(37)=0 : limbSource(37)=1 ;left ear
limbPrecede(38)=0 : limbSource(38)=1 ;right ear

;///////////////////////////////////////////////////////
;--------------------- CHARACTERS ----------------------
;///////////////////////////////////////////////////////
.Characters
Global no_chars
Global no_costumes=8,no_models=5
Global no_hairstyles=31,no_specs=4
;appearance
Dim charModel(optCharLim)
Dim charHeight(optCharLim)
Dim charSpecs(optCharLim)
Dim charAccessory(optCharLim) ;1-6=gangs, 7=warden hat
Dim charHairStyle(optCharLim)
Dim charHair(optCharLim)
Dim charFace(optCharLim)
Dim charCostume(optCharLim) ;0=bare-chested, 1=tight vest, 2=baggy vest, 3=tight t-shirt, 4=baggy t-shirt, 5=tight short sleeve, 6=baggy short sleeve, 7=tight long sleeve, 8=baggy long sleeve
Dim charScar(optCharLim,40)
;attributes
Dim charHealth(optCharLim)
Dim charHP(optCharLim)
Dim charStrength(optCharLim)
Dim charAgility(optCharLim)
Dim charHappiness(optCharLim)
Dim charBreakdown(optCharLim)
Dim charIntelligence(optCharLim)
Dim charReputation(optCharLim)
Dim charOldStrength(optCharLim)
Dim charOldAgility(optCharLim)
Dim charOldIntelligence(optCharLim)
Dim charOldReputation(optCharLim)
Dim charExperience(optCharLim)
;status
Dim charPlayer(optCharLim)
Dim charName$(optCharLim)
Dim charPhoto(optCharLim)
Dim charSnapped(optCharLim)
Dim charRole(optCharLim) ;0=prisoner, 1=warden
Dim charSentence(optCharLim)
Dim charCrime(optCharLim)
Dim charLocation(optCharLim) ;0=dead, 1=north block, 2=yard, 3=east block, 4=study, 5=south block, 6=hospital, 7=west block, 8=kitchen, 9=hall, 10=workshop, 11=toilet
Dim charBlock(optCharLim) ;1=north, 2=east, 3=south, 4=west
Dim charCell(optCharLim)
Dim charGang(optCharLim)
Dim charGangHistory(optCharLim,6)
Dim charRelation(optCharLim,optCharLim) ;0=none, -1=enemy, 1=friend
Dim charAngerTim(optCharLim,optCharLim)
Dim charAttacker(optCharLim)
Dim charWitness(optCharLim)
Dim charFollowTim(optCharLim)
Dim charBribeTim(optCharLim)
Dim charX#(optCharLim)
Dim charY#(optCharLim)
Dim charZ#(optCharLim)
Dim charA#(optCharLim)
Dim charInjured(optCharLim)
Dim charWeapon(optCharLim)
Dim charWeapHistory(optCharLim,30)
Dim charPromo(optCharLim,optCharLim)
Dim charPromoRef(optCharLim)

;///////////////////////////////////////////////////////
;--------------------- GRAPHICS ------------------------
;///////////////////////////////////////////////////////
.Images
;variables
Dim font(10)
Dim fontTest(10)
Global fontNumber,fontComputer
Global fontMoney,fontClock
Dim gLogo(3)
Dim gMenu(10)
Global gTile,gMDickie
Global gHealth,gHappiness
Global gMoney,gPhoto
Global gMap,gMarker
;LOADING PROCESS
Function LoadImages()
 ;main fonts
 font(0)=LoadFont("Arial Cyr",13,0,0,0)
 font(1)=LoadFont("Arial Cyr",16,0,0,0)
 font(2)=LoadFont("Arial Cyr",20,0,0,0)
 font(3)=LoadFont("Arial Cyr",24,0,0,0)
 font(4)=LoadFont("Arial Cyr",36,0,0,0)
 font(5)=LoadFont("Arial Cyr",42,0,0,0)
 font(6)=LoadFont("Arial Cyr",48,0,0,0)
 ;novelty fonts
 fontNumber=LoadFont("Arial Cyr",15,1,0,0)
 fontComputer=LoadFont("Arial Cyr",16,0,0,0)
 fontMoney=LoadFont("Arial Cyr",32,1,0,0)
 fontClock=LoadFont("Arial Cyr",26,1,0,0)
 ;tile
 gTile=LoadImage("Graphics/Tile.png")
 MaskImage gTile,255,0,255
 ;logos
 For count=1 To 3
  gLogo(count)=LoadImage("Graphics/Logo0"+count+".png")
  MaskImage gLogo(count),255,0,255
 Next
 gMDickie=LoadImage("Graphics/MDickie.png")
 MaskImage gMDickie,255,0,255
 ;menu boxes
 For count=1 To 4
  gMenu(count)=LoadImage("Graphics/Menu0"+count+".png")
  MaskImage gMenu(count),255,0,255
 Next
 ;map display
 gMap=LoadImage("Graphics/Map.png")
 MaskImage gMap,255,0,255
 gMarker=LoadImage("Graphics/Marker.png")
 MaskImage gMarker,255,0,255
 ;in-game icons
 Loader(translate("Please Wait"),translate("Loading Images"))
 gHealth=LoadImage("Graphics/Health.png")
 MaskImage gHealth,255,0,255
 gHappiness=LoadImage("Graphics/Happiness.png")
 MaskImage gHappiness,255,0,255
 gMoney=LoadImage("Graphics/Money.png")
 MaskImage gMoney,255,0,255
 gPhoto=LoadImage("Graphics/Photo.png")
 MaskImage gPhoto,255,0,255
 ;game previews
 For count=1 To 3
  gamPhoto(count)=LoadImage("Data/Slot0"+count+"/Photos/Game.bmp")
  If gamPhoto(count)>0 Then MaskImage gamPhoto(count),255,0,255
 Next
End Function

;///////////////////////////////////////////////////////
;--------------------- TEXTURES ------------------------
;///////////////////////////////////////////////////////
.Textures
;count hair textures
Global no_hairs=0
folder=ReadDir(CurrentDir$()+"Characters/Hair/")
 Repeat
  filename$=NextFile$(folder)
  namer$=Upper$(Left$(filename$,4))
  If FileType(CurrentDir$()+"Characters/Hair/"+filename$)=1 And namer$="HAIR" Then no_hairs=no_hairs+1
 Until filename$=""
CloseDir(folder)
;count face textures
Global no_faces=0
folder=ReadDir(CurrentDir$()+"Characters/Faces/")
 Repeat
  filename$=NextFile$(folder)
  namer$=Upper$(Left$(filename$,4))
  If FileType(CurrentDir$()+"Characters/Faces/"+filename$)=1 And namer$="FACE" Then no_faces=no_faces+1
 Until filename$=""
CloseDir(folder)
;count body textures
Global no_bodies=0
folder=ReadDir(CurrentDir$()+"Characters/Bodies/")
 Repeat
  filename$=NextFile$(folder)
  namer$=Upper$(Left$(filename$,4))
  If FileType(CurrentDir$()+"Characters/Bodies/"+filename$)=1 And namer$="BODY" Then no_bodies=no_bodies+1
 Until filename$=""
CloseDir(folder)
;count arm textures
Global no_arms=0
folder=ReadDir(CurrentDir$()+"Characters/Arms/")
 Repeat
  filename$=NextFile$(folder)
  namer$=Upper$(Left$(filename$,3))
  If FileType(CurrentDir$()+"Characters/Arms/"+filename$)=1 And namer$="ARM" Then no_arms=no_arms+1
 Until filename$=""
CloseDir(folder)
;count leg textures
Global no_legs=0
folder=ReadDir(CurrentDir$()+"Characters/Legs/")
 Repeat
  filename$=NextFile$(folder)
  namer$=Upper$(Left$(filename$,4))
  If FileType(CurrentDir$()+"Characters/Legs/"+filename$)=1 And namer$="LEGS" Then no_legs=no_legs+1
 Until filename$=""
CloseDir(folder)
;world variables
Dim tSign(20)
Dim tBlock(4)
Dim tCell(20)
Global tFence,tNet
Global tShower,tCrowd
Dim tScreen(10)
Dim tTray(10)
;weapon variables
Global tMachine,tPistol
;character variables
Global tShaved,tEars,tSeverEars
Dim tEyes(3)
Dim tMouth(5)
Dim tSpecs(3)
Dim tHair(20)
Dim tFace(100)
Dim tBody(100)
Dim tArm(100)
Dim tLegs(100)
Dim tBodyShade(10)
Dim tArmShade(10)
Dim tFaceScar(5)
Dim tBodyScar(5)
Dim tArmScar(5)
Dim tLegScar(5)
Dim tSeverBody(3)
Dim tSeverArm(3)
Dim tSeverLegs(3)
Dim tTattooBody(6)
Dim tTattooVest(6)
Dim tTattooArm(6)
Dim tTattooTee(6)
Dim tTattooSleeve(6)
;LOADING PROCESS
Function LoadTextures()
 Loader(translate("Please Wait"),translate("Loading Numbers"))
 ;signs
 For count=1 To 11
  tSign(count)=LoadTexture("World/Signs/Sign"+Dig$(count,10)+".png")
 Next 
 For count=1 To 4
  tBlock(count)=LoadTexture("Characters/Numbers/Block"+Dig$(count,10)+".png")
 Next
 For count=1 To 20
  tCell(count)=LoadTexture("Characters/Numbers/Cell"+Dig$(count,10)+".png")
 Next 
 ;video screens
 For count=0 To 10
  tScreen(count)=LoadTexture("World/Screens/Screen"+Dig$(count,10)+".JPG")
 Next
 ;food trays
 For count=0 To 7
  tTray(count)=LoadTexture("World/Sprites/Tray"+Dig$(count,10)+".JPG")
 Next 
 ;world
 tFence=LoadTexture("World/Sprites/Fence.png",4)
 tNet=LoadTexture("World/Sprites/Net.png",4)
 tShower=LoadTexture("World/Sprites/Shower.png",4)
 tCrowd=LoadAnimTexture("World/Sprites/Crowd.png",4,512,128,0,4)
 ;weapons
 tMachine=LoadTexture("Weapons/Textures/Machine.png",4)
 tPistol=LoadTexture("Weapons/Textures/Pistol.png",4)
 ;facial expressions
 Loader(translate("Please Wait"),translate("Loading Expressions"))
 tEars=LoadTexture("Characters/Expressions/Ears.JPG")
 For count=1 To 3
  tEyes(count)=LoadTexture("Characters/Expressions/Eyes0"+count+".JPG")
 Next
 For count=0 To 5
  tMouth(count)=LoadTexture("Characters/Expressions/Mouth0"+count+".JPG")
 Next
 ;costume variations
 tShaved=LoadTexture("Characters/Hair/Shaved.JPG")
 For count=1 To 3
  tSpecs(count)=LoadTexture("Characters/Specs/Specs"+Dig$(count,10)+".JPG")
 Next
 For count=1 To no_hairs
  tHair(count)=LoadTexture("Characters/Hair/Hair"+Dig$(count,10)+".png",4)
 Next
 For count=1 To no_faces
  Loader(translate("Please Wait"),translate("Loading Face #FIRST# of #SECOND#", Dig$(count,10), no_faces))
  tFace(count)=LoadTexture("Characters/Faces/Face"+Dig$(count,10)+".JPG")
 Next
 For count=1 To no_bodies
  Loader(translate("Please Wait"),translate("Loading Body #FIRST# of #SECOND#", Dig$(count,10), no_bodies))
  tBody(count)=LoadTexture("Characters/Bodies/Body"+Dig$(count,10)+".JPG")
 Next
 For count=1 To no_arms
  Loader(translate("Please Wait"),translate("Loading Arm #FIRST# of #SECOND#", Dig$(count,10), no_arms))
  tArm(count)=LoadTexture("Characters/Arms/Arm"+Dig$(count,10)+".JPG")
 Next
 For count=1 To no_legs
  Loader(translate("Please Wait"),translate("Loading Body #FIRST# of #SECOND#", Dig$(count,10), no_legs))
  tLegs(count)=LoadTexture("Characters/Legs/Legs"+Dig$(count,10)+".JPG")
 Next
 ;racial shades
 Loader(translate("Please Wait"),translate("Loading Shades"))
 For count=1 To 4
  tBodyShade(count)=LoadTexture("Characters/Shading/Body"+Dig$(count,10)+".png") 
 Next
 For count=1 To 8
  tArmShade(count)=LoadTexture("Characters/Shading/Arm"+Dig$(count,10)+".png") 
 Next
 ;scarring
 Loader(translate("Please Wait"),translate("Loading Scars"))
 For count=0 To 5
  tFaceScar(count)=LoadTexture("Characters/Scarring/Face"+Dig$(count,10)+".JPG") 
 Next
 For count=0 To 4
  tBodyScar(count)=LoadTexture("Characters/Scarring/Body"+Dig$(count,10)+".JPG") 
 Next
 For count=0 To 4
  tArmScar(count)=LoadTexture("Characters/Scarring/Arm"+Dig$(count,10)+".JPG") 
 Next
 For count=0 To 4
  tLegScar(count)=LoadTexture("Characters/Scarring/Legs"+Dig$(count,10)+".JPG") 
 Next
 ;wounds
 tSeverEars=LoadTexture("Characters/Scarring/Wounds/Ears.JPG")
 For count=1 To 3
  tSeverBody(count)=LoadTexture("Characters/Scarring/Wounds/Body"+Dig$(count,10)+".JPG") 
 Next 
 For count=1 To 3
  tSeverArm(count)=LoadTexture("Characters/Scarring/Wounds/Arm"+Dig$(count,10)+".JPG") 
 Next   
 For count=1 To 3
  tSeverLegs(count)=LoadTexture("Characters/Scarring/Wounds/Legs"+Dig$(count,10)+".JPG") 
 Next   
 ;tattoos
 For count=1 To 6
  tTattooBody(count)=LoadTexture("Characters/Tattoos/Body"+Dig$(count,10)+".JPG") 
  tTattooVest(count)=LoadTexture("Characters/Tattoos/Vest"+Dig$(count,10)+".JPG") 
  tTattooArm(count)=LoadTexture("Characters/Tattoos/Arm"+Dig$(count,10)+".JPG") 
  tTattooTee(count)=LoadTexture("Characters/Tattoos/Tee"+Dig$(count,10)+".JPG") 
  tTattooSleeve(count)=LoadTexture("Characters/Tattoos/Sleeve"+Dig$(count,10)+".JPG")   
 Next
End Function

;///////////////////////////////////////////////////////
;--------------------- WEAPONS -------------------------
;///////////////////////////////////////////////////////
.Weapons
Global no_weaps=100,weapList=25
;state
Dim weap(optWeapLim)
Dim weapType(optWeapLim)
Dim weapCarrier(optWeapLim)
Dim weapThrower(optWeapLim)
Dim weapSting(optWeapLim,optPlayLim)
Dim weapClip(optWeapLim)
Dim weapAmmo(optWeapLim)
Dim weapScar(optWeapLim)
Dim weapOldScar(optWeapLim)
Dim weapState(optWeapLim)
Dim weapLocation(optWeapLim)
;physics
Dim weapWall(optWeapLim)
Dim weapGround(optWeapLim)
Dim weapX#(optWeapLim)
Dim weapY#(optWeapLim)
Dim weapZ#(optWeapLim)
Dim weapOldX#(optWeapLim)
Dim weapOldY#(optWeapLim)
Dim weapOldZ#(optWeapLim)
Dim weapA#(optWeapLim)
Dim weapFlight#(optWeapLim)
Dim weapFlightA#(optWeapLim)
Dim weapGravity#(optWeapLim)
Dim weapBounce#(optWeapLim)
;type
Dim weapName$(weapList)
Dim weapFile$(weapList)
Dim weapSound(weapList)
Dim weapTex(weapList)
Dim weapSize#(weapList)
Dim weapWeight#(weapList)
Dim weapValue(weapList)
Dim weapRange#(weapList)
Dim weapDamage(weapList)
Dim weapShiny#(weapList)
Dim weapStyle(weapList) ;0=hand, 1=sword, 2=shield, 3=pistol, 4=rifle, 5=???, 6=TNT, 7=stab
Dim weapHabitat(weapList)
Dim weapCreate(weapList)
;creation kits
Dim kit(6)
Dim kitType(6)
Dim kitState(6)
;DATA
Function LoadWeaponData()
 weapName$(0)=translate("Thing")
 ;rock
 n=1 : weapName$(n)=translate("Rock") : weapFile$(n)="Rock"
 weapSound(n)=sRock : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=5 : weapWeight#(n)=0.4
 weapRange#(n)=6 : weapDamage(n)=3
 weapStyle(n)=0 : weapHabitat(n)=2
 weapCreate(n)=0 : weapValue(n)=10
 ;wooden plank
 n=2 : weapName$(n)=translate("Wooden Plank") : weapFile$(n)="Plank"
 weapSound(n)=sWood : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=8 : weapWeight#(n)=0.3
 weapRange#(n)=8 : weapDamage(n)=3
 weapStyle(n)=1 : weapHabitat(n)=10
 weapCreate(n)=1 : weapValue(n)=10
 ;steel pipe
 n=3 : weapName$(n)=translate("Steel Pipe") : weapFile$(n)="Pipe"
 weapSound(n)=sMetal : weapTex(n)=0 : weapShiny#(n)=1.0
 weapSize#(n)=8 : weapWeight#(n)=0.3
 weapRange#(n)=8 : weapDamage(n)=3
 weapStyle(n)=1 : weapHabitat(n)=10
 weapCreate(n)=1 : weapValue(n)=10
 ;baseball bat
 n=4 : weapName$(n)=translate("Baseball Bat") : weapFile$(n)="Bat"
 weapSound(n)=sWood : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=8 : weapWeight#(n)=0.3
 weapRange#(n)=8 : weapDamage(n)=3
 weapStyle(n)=1 : weapHabitat(n)=2
 weapCreate(n)=1 : weapValue(n)=20
 ;pool cue
 n=5 : weapName$(n)=translate("Pool Cue") : weapFile$(n)="Cue"
 weapSound(n)=sCane : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=12 : weapWeight#(n)=0.25
 weapRange#(n)=10 : weapDamage(n)=2
 weapStyle(n)=1 : weapHabitat(n)=9
 weapCreate(n)=1 : weapValue(n)=20
 ;dagger
 n=6 : weapName$(n)=translate("Knife") : weapFile$(n)="Dagger"
 weapSound(n)=sBlade : weapTex(n)=0 : weapShiny#(n)=1
 weapSize#(n)=6 : weapWeight#(n)=0.25
 weapRange#(n)=6 : weapDamage(n)=4
 weapStyle(n)=7 : weapHabitat(n)=8
 weapCreate(n)=1 : weapValue(n)=20
 ;pistol
 n=7 : weapName$(n)=translate("Pistol") : weapFile$(n)="Pistol"
 weapSound(n)=sGun : weapTex(n)=tPistol : weapShiny#(n)=0.5
 weapSize#(n)=5 : weapWeight#(n)=0.3
 weapRange#(n)=6 : weapDamage(n)=3
 weapStyle(n)=3 : weapHabitat(n)=0
 weapCreate(n)=1 : weapValue(n)=100
 ;machine gun
 n=8 : weapName$(n)=translate("Machine Gun") : weapFile$(n)="Machine"
 weapSound(n)=sGun : weapTex(n)=tMachine : weapShiny#(n)=0.5
 weapSize#(n)=8 : weapWeight#(n)=0.4
 weapRange#(n)=8 : weapDamage(n)=3
 weapStyle(n)=4 : weapHabitat(n)=0
 weapCreate(n)=1 : weapValue(n)=100
 ;TNT
 n=9 : weapName$(n)=translate("Explosive") : weapFile$(n)="TNT"
 weapSound(n)=sGeneric : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=6 : weapWeight#(n)=0.3
 weapRange#(n)=5 : weapDamage(n)=2
 weapStyle(n)=6 : weapHabitat(n)=0
 weapCreate(n)=1 : weapValue(n)=100
 ;brick
 n=10 : weapName$(n)=translate("Brick") : weapFile$(n)="Brick"
 weapSound(n)=sRock : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=6 : weapWeight#(n)=0.4
 weapRange#(n)=6 : weapDamage(n)=3
 weapStyle(n)=0 : weapHabitat(n)=2
 weapCreate(n)=0 : weapValue(n)=10
 ;dumbell
 n=11 : weapName$(n)=translate("Dumbbell") : weapFile$(n)="Dumbell"
 weapSound(n)=sAxe : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=8 : weapWeight#(n)=0.5
 weapRange#(n)=6 : weapDamage(n)=5
 weapStyle(n)=0 : weapHabitat(n)=2
 weapCreate(n)=1 : weapValue(n)=20
 ;nightstick
 n=12 : weapName$(n)=translate("Nightstick") : weapFile$(n)="Baton"
 weapSound(n)=sWood : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=6 : weapWeight#(n)=0.3
 weapRange#(n)=7 : weapDamage(n)=3
 weapStyle(n)=1 : weapHabitat(n)=0
 weapCreate(n)=1 : weapValue(n)=20
 ;hammer
 n=13 : weapName$(n)=translate("Hammer") : weapFile$(n)="Hammer"
 weapSound(n)=sRock : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=5 : weapWeight#(n)=0.4
 weapRange#(n)=6 : weapDamage(n)=4
 weapStyle(n)=1 : weapHabitat(n)=10
 weapCreate(n)=1 : weapValue(n)=20
 ;ball
 n=14 : weapName$(n)=translate("Ball") : weapFile$(n)="Ball"
 weapSound(n)=sBall : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=7 : weapWeight#(n)=0.3
 weapRange#(n)=5 : weapDamage(n)=1
 weapStyle(n)=0 : weapHabitat(n)=99
 weapCreate(n)=1 : weapValue(n)=10
 ;broom
 n=15 : weapName$(n)=translate("Broom") : weapFile$(n)="Broom"
 weapSound(n)=sCane : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=13 : weapWeight#(n)=0.25
 weapRange#(n)=11 : weapDamage(n)=2
 weapStyle(n)=1 : weapHabitat(n)=99
 weapCreate(n)=1 : weapValue(n)=20
 ;cigarette
 n=16 : weapName$(n)=translate("Cigarette") : weapFile$(n)="Cigar"
 weapSound(n)=sCigar : weapTex(n)=0 : weapShiny#(n)=0
 weapSize#(n)=4 : weapWeight#(n)=0.15
 weapRange#(n)=6 : weapDamage(n)=1
 weapStyle(n)=0 : weapHabitat(n)=2
 weapCreate(n)=0 : weapValue(n)=10
 ;syringe
 n=17 : weapName$(n)=translate("Syringe") : weapFile$(n)="Syringe"
 weapSound(n)=sSyringe : weapTex(n)=0 : weapShiny#(n)=0.5
 weapSize#(n)=5 : weapWeight#(n)=0.2
 weapRange#(n)=6 : weapDamage(n)=2
 weapStyle(n)=7 : weapHabitat(n)=6
 weapCreate(n)=0 : weapValue(n)=20
 ;beer bottle
 n=18 : weapName$(n)=translate("Bottle") : weapFile$(n)="Bottle"
 weapSound(n)=sBottle : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=5 : weapWeight#(n)=0.25
 weapRange#(n)=5 : weapDamage(n)=2
 weapStyle(n)=6 : weapHabitat(n)=8
 weapCreate(n)=0 : weapValue(n)=10
 ;fire extinguisher
 n=19 : weapName$(n)=translate("Extinguisher") : weapFile$(n)="Exting"
 weapSound(n)=sMetal : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=6 : weapWeight#(n)=0.3
 weapRange#(n)=5 : weapDamage(n)=3
 weapStyle(n)=6 : weapHabitat(n)=99
 weapCreate(n)=1 : weapValue(n)=50
 ;screwdriver
 n=20 : weapName$(n)=translate("Screwdriver") : weapFile$(n)="Screw"
 weapSound(n)=sBlade : weapTex(n)=0 : weapShiny#(n)=1
 weapSize#(n)=5 : weapWeight#(n)=0.25
 weapRange#(n)=6 : weapDamage(n)=3
 weapStyle(n)=7 : weapHabitat(n)=10
 weapCreate(n)=1 : weapValue(n)=10
 ;scissors
 n=21 : weapName$(n)=translate("Scissor") : weapFile$(n)="Scissors"
 weapSound(n)=sBlade : weapTex(n)=0 : weapShiny#(n)=1
 weapSize#(n)=5 : weapWeight#(n)=0.25
 weapRange#(n)=6 : weapDamage(n)=4
 weapStyle(n)=7 : weapHabitat(n)=4
 weapCreate(n)=1 : weapValue(n)=10
 ;meat cleaver
 n=22 : weapName$(n)=translate("Meat Cleaver") : weapFile$(n)="Cleaver"
 weapSound(n)=sBlade : weapTex(n)=0 : weapShiny#(n)=1
 weapSize#(n)=8 : weapWeight#(n)=0.3
 weapRange#(n)=8 : weapDamage(n)=5
 weapStyle(n)=1 : weapHabitat(n)=8
 weapCreate(n)=1 : weapValue(n)=20
 ;samurai sword
 n=23 : weapName$(n)=translate("Sword") : weapFile$(n)="Samurai"
 weapSound(n)=sBlade : weapTex(n)=0 : weapShiny#(n)=1
 weapSize#(n)=8 : weapWeight#(n)=0.3
 weapRange#(n)=10 : weapDamage(n)=5
 weapStyle(n)=1 : weapHabitat(n)=0
 weapCreate(n)=1 : weapValue(n)=50
 ;comb
 n=24 : weapName$(n)=translate("Comb") : weapFile$(n)="Comb"
 weapSound(n)=sCigar : weapTex(n)=0 : weapShiny#(n)=0.25
 weapSize#(n)=5 : weapWeight#(n)=0.2
 weapRange#(n)=6 : weapDamage(n)=1
 weapStyle(n)=0 : weapHabitat(n)=99
 weapCreate(n)=0 : weapValue(n)=10
 ;mirror
 n=25 : weapName$(n)=translate("Mirror") : weapFile$(n)="Mirror"
 weapSound(n)=sGeneric : weapTex(n)=0 : weapShiny#(n)=0.5
 weapSize#(n)=8 : weapWeight#(n)=0.25
 weapRange#(n)=7 : weapDamage(n)=2
 weapStyle(n)=1 : weapHabitat(n)=99
 weapCreate(n)=1 : weapValue(n)=20
End Function

;///////////////////////////////////////////////////////
;----------------------- WORLD -------------------------
;///////////////////////////////////////////////////////
.World
Global world
Global no_chairs,no_beds,no_doors
Global wScreen,wOldScreen
;food trays
Dim trayState(50)
Dim trayOldState(50)
;camera 
Global camListener,dummy
Global camType,camTim
Global cam,camPivot
Global camFoc,camOldFoc
Global camX#,camY#=75,camZ#
Global camTX#,camTY#,camTZ#
Global camPivX#,camPivY#=100,camPivZ#
Global camPivTX#,camPivTY#,camPivTZ#
Global camRectify
Global camMouseX#,camMouseY#
;smooth co-ordination
Global speedX#,speedY#,speedZ#
;camera presets
Dim camShortcut(10)
camShortcut(1)=1
camShortcut(2)=2
camShortcut(3)=3
camShortcut(4)=4
camShortcut(5)=5
camShortcut(6)=6
camShortcut(7)=7
camShortcut(8)=8
camShortcut(9)=9
camShortcut(10)=0
;lighting
Dim light(10)
Global no_lights
Global lightR#=100,lightG#=100,lightB#=100
Global lightTR#,lightTG#,lightTB#
Global ambR#=100,ambG#=100,ambB#=100
Global ambTR#,ambTG#,ambTB#
Global atmosR#=100,atmosG#=100,atmosB#=100
Global atmosTR#,atmosTG#,atmosTB#
Global skyR#=255,skyG#=255,skyB#=255
Global skyTR#,skyTG#,skyTB#

;////////////////////////////////////////////////////////
;--------------------- DOORS ----------------------------
;////////////////////////////////////////////////////////
.Doors
;data
Dim doorA#(15,10)
Dim doorX1#(15,10)
Dim doorX2#(15,10)
Dim doorY1#(15,10)
Dim doorY2#(15,10)
Dim doorZ1#(15,10)
Dim doorZ2#(15,10)
;blocks
For b=1 To 8
 If b=1 Or b=3 Or b=5 Or b=7
  d=1 : doorA#(b,d)=180
  doorX1#(b,d)=-25 : doorX2#(b,d)=25
  doorY1#(b,d)=0 : doorY2#(b,d)=60
  doorZ1#(b,d)=-350 : doorZ2#(b,d)=-330
 EndIf
 If b=2 Or b=4 Or b=6 Or b=8
  d=1 : doorA#(b,d)=180
  doorX1#(b,d)=65 : doorX2#(b,d)=90
  doorY1#(b,d)=0 : doorY2#(b,d)=60
  doorZ1#(b,d)=190 : doorZ2#(b,d)=220
 EndIf
Next
;study
b=4 : d=1 : doorA#(b,d)=180
doorX1#(b,d)=-10 : doorX2#(b,d)=15
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-160 : doorZ2#(b,d)=-130
b=4 : d=2 : doorA#(b,d)=270
doorX1#(b,d)=135 : doorX2#(b,d)=165
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-12 : doorZ2#(b,d)=12
;hospital
b=6 : d=1 : doorA#(b,d)=180
doorX1#(b,d)=-12 : doorX2#(b,d)=12
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-160 : doorZ2#(b,d)=-130
b=6 : d=2 : doorA#(b,d)=0
doorX1#(b,d)=-12 : doorX2#(b,d)=12
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=135 : doorZ2#(b,d)=165
;kitchen
b=8 : d=1 : doorA#(b,d)=180
doorX1#(b,d)=-12 : doorX2#(b,d)=12
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-360 : doorZ2#(b,d)=-330
;hall
b=9 : d=1 : doorA#(b,d)=0
doorX1#(b,d)=-175 : doorX2#(b,d)=-120
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=285 : doorZ2#(b,d)=310
b=9 : d=2 : doorA#(b,d)=0
doorX1#(b,d)=140 : doorX2#(b,d)=165
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=285 : doorZ2#(b,d)=310
b=9 : d=3 : doorA#(b,d)=270
doorX1#(b,d)=285 : doorX2#(b,d)=310
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=120 : doorZ2#(b,d)=180
b=9 : d=4 : doorA#(b,d)=270
doorX1#(b,d)=285 : doorX2#(b,d)=310
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-160 : doorZ2#(b,d)=-135
b=9 : d=5 : doorA#(b,d)=180
doorX1#(b,d)=125 : doorX2#(b,d)=185
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-310 : doorZ2#(b,d)=-285
b=9 : d=6 : doorA#(b,d)=180
doorX1#(b,d)=-165 : doorX2#(b,d)=-135
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-310 : doorZ2#(b,d)=-285
b=9 : d=7 : doorA#(b,d)=90
doorX1#(b,d)=-310 : doorX2#(b,d)=-285
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-175 : doorZ2#(b,d)=-115
b=9 : d=8 : doorA#(b,d)=90
doorX1#(b,d)=-310 : doorX2#(b,d)=-285
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=140 : doorZ2#(b,d)=165
;workshop
b=10 : d=1 : doorA#(b,d)=180
doorX1#(b,d)=-12 : doorX2#(b,d)=12
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-135 : doorZ2#(b,d)=-105
;toilets
b=11 : d=1 : doorA#(b,d)=180
doorX1#(b,d)=78 : doorX2#(b,d)=101
doorY1#(b,d)=0 : doorY2#(b,d)=60
doorZ1#(b,d)=-85 : doorZ2#(b,d)=-55

;////////////////////////////////////////////////////////
;--------------------- CELLS ----------------------------
;////////////////////////////////////////////////////////
.Cells
;data
Dim cellLocked(15,20)
Dim cellX1#(20)
Dim cellX2#(20)
Dim cellZ1#(20)
Dim cellZ2#(20)
Dim cellY1#(20)
Dim cellY2#(20)
Dim cellDoorX#(20)
Dim cellDoorZ#(20)
;lower left side
n=1
cellX1#(n)=-300 : cellX2#(n)=-205
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=-140 : cellZ2#(n)=-55
cellDoorX#(n)=-195 : cellDoorZ#(n)=-95
n=2
cellX1#(n)=-300 : cellX2#(n)=-205
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=-35 : cellZ2#(n)=48
cellDoorX#(n)=-195 : cellDoorZ#(n)=8
n=3
cellX1#(n)=-300 : cellX2#(n)=-205
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=70 : cellZ2#(n)=150
cellDoorX#(n)=-195 : cellDoorZ#(n)=112
n=4
cellX1#(n)=-300 : cellX2#(n)=-205
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=170 : cellZ2#(n)=255
cellDoorX#(n)=-195 : cellDoorZ#(n)=215
;lower top side
n=5
cellX1#(n)=-195 : cellX2#(n)=-110
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=265 : cellZ2#(n)=355
cellDoorX#(n)=-152 : cellDoorZ#(n)=248
n=6
cellX1#(n)=113 : cellX2#(n)=200
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=265 : cellZ2#(n)=355
cellDoorX#(n)=160 : cellDoorZ#(n)=248
;lower right side
n=7
cellX1#(n)=208 : cellX2#(n)=298
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=172 : cellZ2#(n)=255
cellDoorX#(n)=195 : cellDoorZ#(n)=211
n=8
cellX1#(n)=208 : cellX2#(n)=298
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=68 : cellZ2#(n)=150
cellDoorX#(n)=195 : cellDoorZ#(n)=107
n=9
cellX1#(n)=208 : cellX2#(n)=298
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=-35 : cellZ2#(n)=48
cellDoorX#(n)=195 : cellDoorZ#(n)=3
n=10
cellX1#(n)=208 : cellX2#(n)=298
cellY1#(n)=0 : cellY2#(n)=85
cellZ1#(n)=-140 : cellZ2#(n)=-55
cellDoorX#(n)=195 : cellDoorZ#(n)=-100
;upper translations
For n=11 To 20
 cellX1#(n)=cellX1#(n-10) : cellX2#(n)=cellX2#(n-10)
 cellY1#(n)=100 : cellY2#(n)=200
 cellZ1#(n)=cellZ1#(n-10) : cellZ2#(n)=cellZ2#(n-10)
 cellDoorX#(n)=cellDoorX#(n-10) : cellDoorZ#(n)=cellDoorZ#(n-10)
Next

;////////////////////////////////////////////////////////
;--------------- PARTICLE EFFECTS -----------------------
;////////////////////////////////////////////////////////
.Particles
Global fader,fadeAlpha#,fadeTarget#
;particles
Global no_particles=500
Dim part(1000)
Dim partType(1000)
Dim partX#(1000)
Dim partY#(1000)
Dim partZ#(1000)
Dim partA#(1000)
Dim partGravity#(1000)
Dim partFlight#(1000)
Dim partSize#(1000)
Dim partAlpha#(1000)
Dim partFade#(1000)
Dim partTim(1000)
Dim partState(1000)
;explosions
Global no_explodes=20
Dim exType(no_explodes)
Dim exX#(no_explodes)
Dim exY#(no_explodes)
Dim exZ#(no_explodes)
Dim exTim(no_explodes)
Dim exSource(no_explodes)
Dim exHurt(no_explodes,optPlayLim)
;pools
Global no_pools=50
Dim pool(no_pools)
Dim poolType(no_pools)
Dim poolX#(no_pools)
Dim poolY#(no_pools)
Dim poolZ#(no_pools)
Dim poolA#(no_pools)
Dim poolSize#(no_pools)
Dim poolAlpha#(no_pools)
Dim poolState(no_pools)
;bullets
Global no_bullets=40
Dim bullet(no_bullets)
Dim bulletX#(no_bullets)
Dim bulletY#(no_bullets)
Dim bulletZ#(no_bullets)
Dim bulletXA#(no_bullets)
Dim bulletYA#(no_bullets)
Dim bulletZA#(no_bullets)
Dim bulletState(no_bullets)
Dim bulletTim(no_bullets)
Dim bulletShooter(no_bullets)