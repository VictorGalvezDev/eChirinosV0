package com.dam.echirinosv1.ui.screens.dptos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dam.echirinosv1.R
import com.dam.echirinosv1.ui.components.CustomOutlinedTextField

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DptosMto(
    dptosVM: DptosVM,
    modifier: Modifier = Modifier,
    onShowSnackbar: (String) -> Unit = {},
    onNavUp: () -> Unit = {}
) {
    val uiBusState = dptosVM.uiBusState
    val uiMtoState = dptosVM.uiMtoState
    val contexto = LocalContext.current

    Column(modifier = modifier) {
        OutlinedTextField(
            value = uiMtoState.id,
            onValueChange = { dptosVM.setId(it) },
            enabled = uiBusState.dptoSelected == -1,
            label = { Text(text = stringResource(id = R.string.id_mantenimiento)) },
            isError = !uiMtoState.datosObligatorios,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp)
        )

        CustomOutlinedTextField(
            value = uiMtoState.nombre,
            onValueChange = { dptosVM.setNombre(it) },
            idLabel = R.string.nombre_mantenimiento,
            isError = !uiMtoState.datosObligatorios,
            keyboardType = KeyboardType.Text,
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp)
        )

        CustomOutlinedTextField(
            value = uiMtoState.clave,
            onValueChange = { dptosVM.setClave(it) },
            idLabel = R.string.clave_mantenimiento,
            isError = !uiMtoState.datosObligatorios,
            keyboardType = KeyboardType.Text,
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp)
        )

        Button(
            onClick = {
                val dptoSelected = uiBusState.dptoSelected
                val ok = if (dptoSelected != -1) dptosVM.editar() else dptosVM.alta()
                if (ok) {
                    dptosVM.resetDatos()
                    onNavUp()
                    if (dptoSelected != -1) onShowSnackbar(contexto.getString(R.string.edicion_ok)) else onShowSnackbar(
                        contexto.getString(R.string.alta_ok)
                    )
                } else {
                    if (dptoSelected != -1) onShowSnackbar(contexto.getString(R.string.edicion_ko)) else onShowSnackbar(
                        contexto.getString(R.string.alta_ko)
                    )
                }
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    }
}