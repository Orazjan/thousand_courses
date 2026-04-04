package com.atnzvdev.thousand.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchAndFilterRow(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            placeholder = {
                Text(
                    "Поиск...",
                    color = MaterialTheme.colorScheme.onSecondary.copy(0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    Icons.TwoTone.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary.copy(0.9f)
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            Icons.TwoTone.Clear,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSecondary.copy(0.9f)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(26.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
            ),
            singleLine = true
        )

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier
                .size(52.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(50)
                )
        ) {
            Icon(
                Icons.TwoTone.FilterAlt,
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.onSecondary.copy(0.9f)
            )
        }
    }
}

@Composable
fun SortRow(
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSortClick() }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "По дате добавления",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            imageVector = Icons.TwoTone.SwapVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,

            )
    }
}