package com.dam.echirinosv1.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dam.echirinosv1.R
import com.dam.echirinosv1.ui.components.DlgConfirmacion
import com.dam.echirinosv1.ui.screens.aulas.AulasBus
import com.dam.echirinosv1.ui.screens.aulas.AulasMto
import com.dam.echirinosv1.ui.screens.aulas.AulasVM
import com.dam.echirinosv1.ui.screens.dptos.DptosBus
import com.dam.echirinosv1.ui.screens.dptos.DptosMto
import com.dam.echirinosv1.ui.screens.dptos.DptosVM
import com.dam.echirinosv1.ui.screens.incs.IncsBus
import com.dam.echirinosv1.ui.screens.incs.IncsMto
import com.dam.echirinosv1.ui.screens.incs.IncsVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class AppScreens(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Login(title = R.string.title_login),
    DptosBus(title = R.string.title_dptosbus),
    DptosMto(title = R.string.title_dptosmto),
    AulasBus(title = R.string.title_aulasbus),
    AulasMto(title = R.string.title_aulasmto),
    IncsBus(title = R.string.title_incsbus),
    IncsMto(title = R.string.title_incsmto)
}

@Composable
fun MainApp(
    mainVM: MainVM,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val dptosVM: DptosVM = viewModel(factory = DptosVM.Factory)
    val aulasVM: AulasVM = viewModel(factory = AulasVM.Factory)
    val incsVM: IncsVM = viewModel(factory = IncsVM.Factory)

    val uiMainState = mainVM.uiMainState
    val configuration = LocalConfiguration.current
    val navTypeSelection =
        windowSize.widthSizeClass == WindowWidthSizeClass.Compact
                && configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreens.valueOf(
        backStackEntry?.destination?.route ?: AppScreens.Home.name
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.nav_home_label)) },
                    selected = currentScreen == AppScreens.Home,
                    onClick = { navController.navigate(AppScreens.Home.name) { popUpTo(0) } },
                    icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = null) })

                if (uiMainState.login != null) {
                    if (uiMainState.login.id == 0) {
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(id = R.string.nav_dptosbus_label)) },
                            selected = currentScreen == AppScreens.DptosBus,
                            onClick = { navController.navigate(AppScreens.DptosBus.name) { popUpTo(0) } },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Apartment,
                                    contentDescription = null
                                )
                            })
                    }
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(id = R.string.nav_aulasbus_label)) },
                        selected = currentScreen == AppScreens.AulasBus,
                        onClick = { navController.navigate(AppScreens.AulasBus.name) { popUpTo(0) } },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.School,
                                contentDescription = null
                            )
                        })

                    NavigationDrawerItem(
                        label = { Text(text = stringResource(id = R.string.nav_incsbus_label)) },
                        selected = currentScreen == AppScreens.IncsBus,
                        onClick = { navController.navigate(AppScreens.IncsBus.name) { popUpTo(0) } },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.AddAlert,
                                contentDescription = null
                            )
                        })
                }
            }
        },
        gesturesEnabled = !navTypeSelection
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                MainTopAppBar(
                    currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    mainVM = mainVM,
                    navController = navController,
                    scope = scope,
                    drawerState = drawerState,
                    navTypeSelection = navTypeSelection
                ) {
                    navController.navigateUp()
                }
            },

            bottomBar = {
                if (navTypeSelection) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentScreen == AppScreens.Home,
                            onClick = {
                                navController.navigate(AppScreens.Home.name) {
                                    popUpTo(0)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.nav_home_label)) },
                        )
                        NavigationBarItem(
                            selected = currentScreen == AppScreens.DptosBus,
                            onClick = {
                                navController.navigate(AppScreens.DptosBus.name) {
                                    popUpTo(0)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Apartment,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.nav_dptosbus_label)) },
                            enabled = uiMainState.login != null && uiMainState.login.id == 0
                        )

                        NavigationBarItem(
                            selected = currentScreen == AppScreens.AulasBus,
                            onClick = {
                                navController.navigate(AppScreens.AulasBus.name) {
                                    popUpTo(0)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.School,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.nav_aulasbus_label)) },
                            enabled = uiMainState.login != null
                        )

                        NavigationBarItem(
                            selected = currentScreen == AppScreens.IncsBus,
                            onClick = {
                                navController.navigate(AppScreens.IncsBus.name) {
                                    popUpTo(0)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.AddAlert,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.nav_incsbus_label)) },
                            enabled = uiMainState.login != null
                        )
                    }
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        ) {
            NavHost(
                navController = navController,
                startDestination = AppScreens.Login.name,
                modifier = Modifier.padding(it)
            ) {
                composable(route = AppScreens.Home.name) {
                    HomeScreen(
                        mainVM = mainVM
                    )
                }
                composable(route = AppScreens.Login.name) {
                    LoginScreen(
                        mainVM = mainVM,
                        navHome = {
                            navController.navigate(AppScreens.Home.name) {
                                popUpTo(0)
                            }
                        },
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavUp = { navController.navigateUp() }
                    )
                }
                composable(route = AppScreens.DptosBus.name) {
                    DptosBus(
                        dptosVM = dptosVM,
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavDown = { navController.navigate(AppScreens.DptosMto.name) }
                    )
                }
                composable(route = AppScreens.DptosMto.name) {
                    DptosMto(
                        dptosVM = dptosVM,
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavUp = {
                            navController.navigateUp()
                        }
                    )
                }
                composable(route = AppScreens.AulasBus.name) {
                    AulasBus(
                        aulasVM = aulasVM,
                        mainVM = mainVM,
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavDown = {
                            navController.navigate(AppScreens.AulasMto.name)
                        }
                    )
                }
                composable(route = AppScreens.AulasMto.name) {
                    AulasMto(
                        aulasVM = aulasVM,
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavUp = {
                            navController.navigateUp()
                        }
                    )
                }
                composable(route = AppScreens.IncsBus.name) {
                    IncsBus(
                        incsVM = incsVM,
                        mainVM = mainVM,
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavDown = {
                            navController.navigate(AppScreens.IncsMto.name)
                        }
                    )
                }
                composable(route = AppScreens.IncsMto.name) {
                    IncsMto(
                        incsVM = incsVM,
                        idDpto = uiMainState.login!!.id,
                        onShowSnackbar = { scope.launch { snackBarHostState.showSnackbar(it) } },
                        onNavUp = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
    BackHandler {
        mainVM.setShowDlgSalir(true)
    }
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    currentScreen: AppScreens,
    canNavigateBack: Boolean,
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navTypeSelection: Boolean,
    mainVM: MainVM,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {
    val activity = LocalContext.current as? Activity
    val uiMainState = mainVM.uiMainState

    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            } else if (!navTypeSelection) {
                IconButton(onClick = {
                    scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                }
            }
        },
        actions = {
            IconButton(onClick = {
                mainVM.setShowDlgSalir(true)
            }) {
                Icon(imageVector = Icons.Filled.PowerSettingsNew, contentDescription = null)
            }

            IconButton(onClick = {
                if (uiMainState.showMenu) mainVM.setShowMenu(false) else mainVM.setShowMenu(
                    true
                )
            }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }

            DropdownMenu(
                expanded = uiMainState.showMenu,
                onDismissRequest = {
                    mainVM.setShowMenu(false)
                }) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.menu_login)) },
                    onClick = {
                        navController.navigate(AppScreens.Login.name)
                        mainVM.resetDatos()
                        mainVM.setShowMenu(false)
                    },
                    enabled = navController.currentDestination != navController.findDestination(
                        AppScreens.Login.name
                    )
                )
            }
            if (uiMainState.showDlgSalir) {
                DlgConfirmacion(
                    mensaje = R.string.txt_salir,
                    botonAceptarText = R.string.but_aceptar,
                    botonCancelarText = R.string.but_cancelar,
                    onCancelarClick = {
                        mainVM.setShowDlgSalir(false)
                    },
                    onAceptarClick = {
                        mainVM.setShowDlgSalir(false)
                        activity?.finish()
                    },
                    onDimmissRequest = { mainVM.setShowDlgSalir(false) })
            }
        }
    )
}
