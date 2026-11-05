/*
 * Image.kt
 * Copyright (c) 2026. All rights reserved
 */
package com.kmp.core.design.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.infinum.arkive.annotations.ArkiveComposable
import com.kmp.core.design.dimen.Dimens
import com.kmp.core.design.generated.resources.Res
import com.kmp.core.design.generated.resources.ic_test_1
import com.kmp.core.design.generated.resources.ic_test_2
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Image(
    modifier: Modifier = Modifier,
    size: Dp,
    image: Painter,
) {
    Image(
        modifier = modifier
            .size(size = size),
        painter = image,
        contentDescription = null,
    )
}

@Composable
fun Image(
    modifier: Modifier = Modifier,
    size: Dp,
    image: DrawableResource,
) {
    Image(
        modifier = modifier
            .size(size = size),
        painter = painterResource(resource = image),
        contentDescription = null,
    )
}

@Composable
fun ImageCircular(
    modifier: Modifier = Modifier,
    size: Dp,
    image: Painter,
) {
    Image(
        modifier = modifier
            .size(size = size)
            .clip(shape = CircleShape),
        painter = image,
        contentDescription = null,
    )
}

@Composable
fun ImageCircular(
    modifier: Modifier = Modifier,
    size: Dp,
    image: DrawableResource,
) {
    Image(
        modifier = modifier
            .size(size = size)
            .clip(shape = CircleShape),
        painter = painterResource(resource = image),
        contentDescription = null,
    )
}

@Composable
fun ImageAsync(
    modifier: Modifier = Modifier,
    size: Dp,
    placeholder: Painter,
    urlImage: String,
) {
    AsyncImage(
        modifier = modifier.size(size),
        model = urlImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        fallback = placeholder,
        error = placeholder,
    )
}

@Composable
fun ImageAsync(
    modifier: Modifier = Modifier,
    size: Dp,
    placeholder: DrawableResource,
    urlImage: String,
) {
    AsyncImage(
        modifier = modifier.size(size),
        model = urlImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        fallback = painterResource(placeholder),
        error = painterResource(placeholder),
    )
}

@Composable
fun ImageCircularAsync(
    modifier: Modifier = Modifier,
    size: Dp,
    placeholder: Painter,
    urlImage: String,
) {
    AsyncImage(
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        model = urlImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        fallback = placeholder,
        error = placeholder,
    )
}

@Composable
fun ImageCircularAsync(
    modifier: Modifier = Modifier,
    size: Dp,
    placeholder: DrawableResource,
    urlImage: String,
) {
    AsyncImage(
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        model = urlImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        fallback = painterResource(placeholder),
        error = painterResource(placeholder),
    )
}

@ArkiveComposable(
    name = "Image",
    group = "Images",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun ImagePreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        Image(
            size = Dimens.height96,
            image = Res.drawable.ic_test_1,
        )
    }
}

@ArkiveComposable(
    name = "Image Circular",
    group = "Images",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun ImageCircularPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        ImageCircular(
            size = Dimens.height96,
            image = Res.drawable.ic_test_1,
        )
    }
}

@ArkiveComposable(
    name = "Image Async",
    group = "Images",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun ImageAsyncPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        ImageAsync(
            size = Dimens.height96,
            placeholder = Res.drawable.ic_test_2,
            urlImage = "",
        )
    }
}

@ArkiveComposable(
    name = "Image Circular Async",
    group = "Images",
    tags = ["tag"],
    designNodeId = "2001-8",
)
@Preview(
    showBackground = true,
)
@Composable
fun ImageCircularAsyncPreview() {
    Box(
        modifier = Modifier.padding(all = Dimens.padding16),
    ){
        ImageCircularAsync(
            size = Dimens.height96,
            urlImage = "",
            placeholder = Res.drawable.ic_test_2,
        )
    }
}
